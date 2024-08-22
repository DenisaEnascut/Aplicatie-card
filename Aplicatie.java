import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aplicatie extends JFrame {

    private JTextArea resultArea;

    public Aplicatie() {
        // Configurare fereastră principală
        setTitle("Aplicatie");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurare buton de încărcare fișier
        JButton uploadButton = new JButton("Upload Document");
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadFile();
            }
        });

        // Configurare zonă de afișare rezultat
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Adăugare componente în fereastră
        setLayout(new BorderLayout());
        add(uploadButton, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Funcția pentru încărcarea fișierului
    private void uploadFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            processFile(selectedFile);
        }
    }

    // Funcția pentru procesarea fișierului
    private void processFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder data = new StringBuilder();
            String line;

            // Citirea fiecărei linii din fișier și adăugarea la StringBuilder
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }

            // Procesarea datelor pentru extragerea informațiilor necesare
            String cardNumber = extractCardNumber(data.toString());
            ArrayList<String> users = extractUsers(data.toString());
            String validTo = extractValidTo(data.toString());

            // Afișarea rezultatului procesării în format JSON
            displayResult(cardNumber, users, validTo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
        }
    }

    // Funcția pentru extragerea numărului de card
    // Această funcție utilizează un pattern regex pentru a găsi un număr de card Medicare.
    // Formatul așteptat este XXXX XXXXX X, unde X este o cifră. Patternul este construit
    // astfel încât să ignore spațiile potențiale dintre grupurile de cifre.
    private String extractCardNumber(String data) {
        Pattern pattern = Pattern.compile("\\b\\d{4}\\s?\\d{5}\\s?\\d\\b");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "Not found";
        }
    }

    // Funcția pentru extragerea utilizatorilor
    // Această funcție caută nume în formatul "Nr. FirstName M LastName"
    // și elimină duplicatele.
    private ArrayList<String> extractUsers(String data) {
        ArrayList<String> users = new ArrayList<>();
        Set<String> uniqueNames = new HashSet<>();
        int userNumber = 1;

        // Pattern pentru a găsi utilizatorii, ignorând numărul la început
        Pattern pattern = Pattern.compile("\\d\\s+([A-Z][a-zA-Z]+)\\s+([A-Z])\\s+([A-Z][a-zA-Z]+)");
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            String firstName = matcher.group(1);
            String middleInitial = matcher.group(2);
            String lastName = matcher.group(3);

            // Construim string-ul user fără număr pentru a verifica unicitatea
            String userKey = firstName + " " + middleInitial + " " + lastName;

            // Adăugăm utilizatorul doar dacă nu este deja în Set
            if (uniqueNames.add(userKey)) {
                String user = userNumber + " " + userKey;
                users.add(user);
                userNumber++; // Incrementăm numărul utilizatorului
            }
        }
        return users;
    }

    // Funcția pentru extragerea datei de expirare
    // Utilizăm un pattern regex pentru a găsi o dată în formatul MM/YYYY.
    private String extractValidTo(String data) {
        Pattern pattern = Pattern.compile("\\b\\d{2}/\\d{4}\\b");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "Not found";
        }
    }

    // Funcția pentru afișarea rezultatului în format JSON
    // Construiți o structură JSON din datele procesate și afișați-o în JTextArea.
    private void displayResult(String cardNumber, ArrayList<String> users, String validTo) {
        resultArea.setText(""); // Curățare text existent
    
        // Afișează numărul cardului
        resultArea.append("               NUMBER CARD:  " + cardNumber + "\n");
    
        // Afișează utilizatorii
        resultArea.append("USERS :  " +  "\n");
        for (String user : users) {
            resultArea.append("    - " + user + "\n");
        }
    
        // Afișează data de expirare
        resultArea.append("                                       VALID TO:  " + validTo + "\n");
    }

    // Funcția principală
    // Creează și afișează fereastra principală.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Aplicatie().setVisible(true);
            }
        });
    }
}
