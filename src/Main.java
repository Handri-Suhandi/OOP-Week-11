import java.util.ArrayList;
import java.util.Scanner;
import exceptions.AuthenticationException;
import exceptions.ExcessiveFailedLoginException;

public class Main {

    private static ArrayList<User> listOfUser = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        initialize();

        while (true) {
            System.out.println("Menu Utama");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.print("Pilihan : ");

            int pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleSignUp();
                    break;
                default:
                    System.out.println("Pilihan tidak valid");
            }
        }
    }

    // -----------------------------------------------------
    // 1. initialize() – menambah user default John Doe
    // -----------------------------------------------------
    private static void initialize() {
        User defaultUser = new User(
                "John",
                "Doe",
                'L',
                "Jl. Merpati No. 1 RT 1 RW 1, Banten",
                "admin",
                "admin"
        );
        listOfUser.add(defaultUser);
    }

    // -----------------------------------------------------
    // 2. handleLogin() – mencoba login ke setiap user
    // -----------------------------------------------------
    private static void handleLogin() {
        System.out.println("\nMenu Login");
        System.out.print("Username : ");
        String username = input.nextLine();
        System.out.print("Password : ");
        String password = input.nextLine();

        try {
            for (User u : listOfUser) {
                boolean success = u.login(username, password);

                if (success) {
                    System.out.println(u.greeting());
                    return;
                }
            }

            // Jika sudah di-loop semua user tetapi belum berhasil login
            throw new AuthenticationException("Akun tidak ditemukan atau password salah!");

        } catch (ExcessiveFailedLoginException ex) {
            System.out.println(ex.getMessage());
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // -----------------------------------------------------
    // 3. handleSignUp() – mendaftarkan user baru
    // -----------------------------------------------------
    private static void handleSignUp() {

        System.out.println("\nMenu Sign Up");

        System.out.print("Nama Depan : ");
        String fname = input.nextLine();

        System.out.print("Nama Belakang : ");
        String lname = input.nextLine();

        System.out.print("Jenis Kelamin (L/P) : ");
        char gender = input.nextLine().toUpperCase().charAt(0);

        System.out.print("Alamat : ");
        String address = input.nextLine();

        System.out.print("Username : ");
        String username = input.nextLine();

        // Username minimal 8 huruf
        if (username.length() < 8) {
            System.out.println("Username harus lebih dari 8 karakter");
            return;
        }

        System.out.print("Password : ");
        String password = input.nextLine();

        // Password harus 6–16 karakter, mengandung huruf besar + angka
        if (!isValidPassword(password)) {
            System.out.println("Password harus mengandung huruf besar, angka, minimal 6 karakter dan maksimum 16 karakter");
            return;
        }

        // Jika valid → buat user baru
        User newUser = new User(fname, lname, gender, address, username, password);
        listOfUser.add(newUser);

        System.out.println("User telah berhasil didaftarkan");
    }

    // -----------------------------------------------------
    // Cek password valid sesuai contoh
    // -----------------------------------------------------
    private static boolean isValidPassword(String password) {
        if (password.length() < 6 || password.length() > 16) return false;

        boolean hasUpper = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        return hasUpper && hasDigit;
    }
}
