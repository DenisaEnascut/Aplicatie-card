# Aplicatie card

Descriere
Reprezintă o aplicație Java care permite încărcarea și procesarea unui fișier text ce conține date brute extrase dintr-o scanare. Aplicația extrage și afișează informații precum numărul cardului, utilizatorii asociați și data de expirare, utilizând o interfață grafică prietenoasă (GUI).

Funcționalități

Încărcarea fișierului: Permite utilizatorului să încarce un fișier text (.txt).
Procesarea datelor: Extragerea automată a numărului de card, a utilizatorilor și a datei de expirare folosind regex.
Afișare date: Datele procesate sunt afișate într-o fereastră GUI.

Cerințe de Sistem
Java SE 8 sau o versiune mai recentă.
Mediu de dezvoltare: Eclipse, IntelliJ IDEA, sau orice alt IDE Java compatibil.

Utilizare
Deschide aplicația după ce a fost compilată și rulată.

Apasă butonul "Upload Document" pentru a selecta un fișier .txt care conține datele scanate.

Aplicația va procesa automat datele și va afișa rezultatele în fereastra text.

            NUMBER CARD:  1234 56789 1
USERS:
    - 1 JOHN A CITIZEN
    - 2 JAME A CITIZEN
    - 3 JAMES A CITIZEN
    - 4 JESSICA A CITIZEN
                                    VALID TO: 09/2020
Structura Proiectului
Aplicatie.java: Clasa principală care conține logica aplicației, inclusiv încărcarea fișierului, procesarea datelor și afișarea rezultatului.
resources/scanned_data.txt: Un fișier de test care conține date brute ce pot fi utilizate pentru verificarea funcționalității aplicației.
