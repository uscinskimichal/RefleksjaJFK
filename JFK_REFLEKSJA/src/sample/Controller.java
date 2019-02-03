package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


public class Controller {

    private final TreeItem<Object> emptyRoot = new TreeItem<>();
    private final ArrayList<Method> methodsList = new ArrayList<>();
    private URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
    private Class urlClass = URLClassLoader.class;
    private Method addURL = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});

    public Controller() throws NoSuchMethodException {
        addURL.setAccessible(true);
    }

    @FXML
    private Text directoryLabel;

    @FXML
    private Button runButton;

    @FXML
    private TreeView<Object> treeView;

    @FXML
    private TextArea firstParameter;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextArea secondParameter;

    @FXML
    private Label resultLabel;

    @FXML
    private void exitApp() {
        System.exit(0);
    }

    @FXML
    private void openFile() throws Exception {

        descriptionArea.clear();
        DirectoryChooser directory = new DirectoryChooser();
        directory.setInitialDirectory(new File("./"));
        directory.setTitle("Open Resource File");
        File folder = directory.showDialog(Main.mainStage);

        if (folder != null) {
            directoryLabel.setText("Directory: " + folder.getAbsolutePath());
            getFolderContent(folder);
        } else
            directoryLabel.setText("");
    }

    private void getFolderContent(File folder) throws Exception {
        treeView.setRoot(emptyRoot);
        treeView.setShowRoot(false);

        treeView.getRoot().getChildren().clear();
        runButton.setDisable(true);

        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.getName().endsWith(".jar")) {
                TreeItem<Object> head = new TreeItem<>(fileEntry.getName());
                emptyRoot.getChildren().add(head);
                printJar(fileEntry.getAbsolutePath(), head);
            }
        }
    }

    private void printJar(String path, TreeItem<Object> root) throws Exception {
        JarInputStream myJar = new JarInputStream(new FileInputStream(path));
        JarEntry jarEntry;

        File file = new File(path);
        URL url = file.toURI().toURL();

        addURL.invoke(urlClassLoader, new Object[]{url});

        while (true) {
            jarEntry = myJar.getNextJarEntry();

            if (jarEntry == null)
                return;

            else if ((jarEntry.getName().endsWith(".class"))) {
                String myClass = jarEntry.getName().substring(0, jarEntry.getName().length() - 6).replace("/", ".");

                TreeItem<Object> element = new TreeItem<>(myClass);
                root.getChildren().add(element);

                Class aClass = Class.forName(myClass);
                getMethodsOfClass(aClass, element);
            }
        }
    }

    private void getMethodsOfClass(Class aClass, TreeItem<Object> root) {
        try {
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method_ : methods) {
                TreeItem<Object> method = new TreeItem<>(method_);
                methodsList.add(method_);
                root.getChildren().add(method);
            }
            runButton.setDisable(false);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void showDescription() {
        if (treeView.getSelectionModel().getSelectedItem() != null && treeView.getSelectionModel().getSelectedItem().isLeaf()) {
            for (int i = 0; i < methodsList.size(); i++) {
                if (methodsList.get(i)==treeView.getSelectionModel().getSelectedItem().getValue()) {
                    if (!methodsList.get(i).isAnnotationPresent(Description.class)) {
                        descriptionArea.setText("No additional information specified");
                        return;
                    } else {
                        Description description = methodsList.get(i).getAnnotation(Description.class);
                        descriptionArea.setText("Description: " + description.description());
                        return;
                    }
                }
            }
        } else
            descriptionArea.clear();
    }

    @FXML
    private void doOperation() throws Exception {
        Class aClass;
        if (treeView.getSelectionModel().getSelectedItem() != null && treeView.getSelectionModel().getSelectedItem().isLeaf() && (!firstParameter.getText().isEmpty() && !secondParameter.getText().isEmpty())) {
            try {
                String myClass = treeView.getSelectionModel().getSelectedItem().getParent().getValue().toString();
                aClass = Class.forName(myClass);

            } catch (Exception classNotFound) {
                throw new NotChosenMethodException("Choose a method from available classes!");
            }

            if (!IArithmeticOperation.class.isAssignableFrom(aClass)) {
                resultLabel.setText("ERROR, class does not implement the contract.");
                throw new ClassDoesntImplementInterfaceException(aClass.toString() + " --- > does not implement the contract.");
            }

            try {
                for (Method MethodsList_ : methodsList) {
                    if (MethodsList_==treeView.getSelectionModel().getSelectedItem().getValue()) {
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        resultLabel.setText(String.valueOf(decimalFormat.format(MethodsList_.invoke(aClass.newInstance(), Double.valueOf(firstParameter.getText()), Double.valueOf(secondParameter.getText())))));
                        break;
                    }
                }
            } catch (NumberFormatException exception) {
                System.out.println("Input double number, please");
            }
        } else
            System.out.println("Choose a method from available classes and fill the input boxes.");
    }
}
