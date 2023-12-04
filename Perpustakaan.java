import java.util.*;
import java.lang.reflect.Array;
import java.text.*;

public class Perpustakaan {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        // Deklarasi dan Inisialisasi Variabel
        String[] nama = new String[0];
        String[] nim = new String[0];
        String[] username = new String[0];
        String[] passwordPengguna = new String[0];
        String namaMahasiswa = "";
        String nimMhs = "";
        boolean login = false;
        boolean isPetugas = false;

        ArrayList<String[]> dataBuku = new ArrayList<>(Arrays.asList(
                new String[] { "C#", "A001", "Indira", "2020", "Explorer", "1", "0", null, "false" },
                new String[] { "Java", "A002", "Nafa", "2022", "ex", "5", "0", null, "false" }));

        ArrayList<String> antrianPeminjaman = new ArrayList<>();
        ArrayList<String> riwayatAntrian = new ArrayList<>();

        int dendaPerMenit = 10000;
        Date tanggalPengembalian = new Date();
        boolean bukuTersedia = false; // sebagai penanda judul buku ditemukan atau tidak

        // Buka Sistem
        System.out.println("SISTEM PERPUSTAKAAN");

        // Registrasi
        System.out.println("=== Registrasi ===");
        System.out.print("Apakah Anda petugas atau mahasiswa? (petugas/mahasiswa): ");
        String pengguna = scan.next();

        if (pengguna.equalsIgnoreCase("petugas")) {
            // Registrasi sebagai petugas
            System.out.print("Masukkan Nama: ");
            scan.nextLine(); // Memberikan buffer
            nama = Arrays.copyOf(nama, nama.length + 1);
            nama[nama.length - 1] = scan.nextLine(); // Baca nama

            System.out.print("Masukkan Username: ");
            username = Arrays.copyOf(username, username.length + 1);
            username[username.length - 1] = scan.nextLine(); // Baca username

            System.out.print("Masukkan Password: ");
            passwordPengguna = Arrays.copyOf(passwordPengguna, passwordPengguna.length + 1);
            passwordPengguna[passwordPengguna.length - 1] = scan.nextLine(); // Baca password

            System.out.println("Registrasi sebagai petugas berhasil.");

        } else if (pengguna.equalsIgnoreCase("mahasiswa")) {
            // Registrasi sebagai mahasiswa
            scan.nextLine(); // Digunakan untuk membersihkan buffer

            System.out.print("Masukkan Nama: ");
            String namaMhs = scan.nextLine(); // Baca nama

            System.out.print("Masukkan NIM: ");
            nimMhs = scan.nextLine(); // Baca NIM

            System.out.print("Masukkan Username: ");
            String usernameMhs = scan.nextLine(); // Baca username

            System.out.print("Masukkan Password: ");
            String passwordMhs = scan.nextLine(); // Baca password

            // Menyimpan informasi ke dalam array sesuai dengan pengguna
            nama = Arrays.copyOf(nama, nama.length + 1);
            nama[nama.length - 1] = namaMhs;

            username = Arrays.copyOf(username, username.length + 1);
            username[username.length - 1] = usernameMhs;

            passwordPengguna = Arrays.copyOf(passwordPengguna, passwordPengguna.length + 1);
            passwordPengguna[passwordPengguna.length - 1] = passwordMhs;

            nim = Arrays.copyOf(nim, nim.length + 1);
            nim[nim.length - 1] = nimMhs;

            System.out.println("Registrasi sebagai mahasiswa berhasil.");
        } else {
            System.out.println("Input tidak valid. Registrasi gagal.");
        }
        // System.out.println("Registrasi berhasil.");

        // Login
        while (!login) {
            System.out.print("\n=== Login ===");
            System.out.print("\nMasukkan Username anda: ");
            String user = scan.next();
            System.out.print("Masukkan Password anda: ");
            String password = scan.next();

            boolean userFound = false; // Sebagai tanda apakah user ditemukan atau tidak

            for (int i = 0; i < username.length; i++) {
                if (user.equals(username[i]) && password.equals(passwordPengguna[i])) {
                    System.out.println("Login berhasil. Selamat datang, " + nama[i] + "!");
                    namaMahasiswa = nama[i];
                    nimMhs = username[i];
                    login = true;
                    userFound = true;

                    // Penentuan user (Petugas atau mahasiswa)
                    if (pengguna.equalsIgnoreCase("petugas")) {
                        isPetugas = true; // Petugas
                    } else {
                        isPetugas = false; // Mahasiswa
                    }
                    break;
                }
            }

            if (!login) {
                System.out.println("Login gagal. Cek kembali username dan password Anda!");
            }
        }

        // Menu
        boolean type = true;
        while (type) {

            System.out.println("\n==========MENU==========");
            System.out.println("1. Informasi Buku");
            System.out.println("2. Tambah Data Buku");
            System.out.println("3. Pinjam Buku");
            System.out.println("4. Kembalikan Buku");
            System.out.println("5. Riwayat Antrian");
            System.out.println("6. Hapus Data Buku");
            System.out.println("7. Keluar");
            System.out.print("\nPilih menu (1/2/3/4/5): ");

            int perintah = scan.nextInt();

            switch (perintah) {
                // Case ke-1 "Informasi Buku" meliputi data buku dan status buku
                case 1:
                    System.out.println("\n==========INFORMASI BUKU==========");
                    for (int i = 0; i < dataBuku.size(); i++) {
                        String[] book = dataBuku.get(i);
                        System.out.println(
                                (i + 1) + ". " + "Judul buku: " + book[0] + ", " + "Kode buku: " + book[1] + ", "
                                        + "Nama pengarang: " + book[2] + ", " + "Tahun terbit: " + book[3] + ", "
                                        + "Nama penerbit: " + book[4] + ", " + "Stok: " + book[5]);
                        boolean statusPinjam = Boolean.parseBoolean(book[8]);
                        if (statusPinjam) {
                            System.out.println((" (Buku Sedang Dipinjam) "));
                        } else {
                            System.out.println(" (Buku Tersedia) ");
                        }
                    }
                    break;

                // Case ke-2 "Tambah Buku"
                case 2:
                    if (isPetugas) {
                        System.out.println("\n==========TAMBAH BUKU==========");
                        System.out.print("Masukkan judul buku\t: ");
                        String title = scan.next();
                        System.out.print("Masukkan kode buku\t: ");
                        String code = scan.next();
                        System.out.print("Masukkan nama pengarang\t: ");
                        String author = scan.next();
                        System.out.print("Masukkan tahun terbit\t: ");
                        int year = scan.nextInt();
                        System.out.print("Masukkan nama penerbit\t: ");
                        String publisher = scan.next();
                        System.out.print("Masukkan jumlah stok\t: ");
                        int stock = scan.nextInt();

                        // statement ini digunakan untuk menyimpan data buku yang baru ditambahkan ke
                        // dalam ArrayList
                        String[] bukuBaru = { title, code, author, String.valueOf(year), publisher,
                                String.valueOf(stock), "0", null, "false" };
                        dataBuku.add(bukuBaru);

                        System.out.println("Judul buku: " + title + ", " + "Kode buku: " + code + ", "
                                + "Nama pengarang: " + author + ", " + "Tahun terbit: " + year + ", "
                                + "Nama penerbit: " + publisher + ", " + "Stok: " + stock + " Berhasil ditambahkan");
                    } else {
                        System.out.println("Maaf, Anda tidak memiliki akses untuk fitur ini.");
                    }
                    break;

                // Case ke-3 (Form Peminjaman Buku)
                case 3:
                    System.out.println("\n==========FORM PEMINJAMAN==========");
                    System.out.println("Nama Mahasiswa\t: " + namaMahasiswa);
                    System.out.println("NIM\t\t: " + nimMhs);
                    System.out.print("\nMasukkan judul buku yang ingin dipinjam: ");
                    scan.nextLine();
                    String judulPinjam = scan.nextLine();

                    // boolean bukuTersedia = false; // sebagai penanda judul buku ditemukan atau
                    // tidak

                    for (int i = 0; i < dataBuku.size(); i++) {
                        String[] book = dataBuku.get(i);
                        if (judulPinjam.equalsIgnoreCase(book[0])) {
                            bukuTersedia = true;
                            boolean statusPinjam = Boolean.parseBoolean(book[8]);
                            if (Integer.parseInt(book[5]) > 0 && !statusPinjam) {
                                book[8] = "true"; // indeks status buku
                                book[6] = String.valueOf(Integer.parseInt(book[6]) + 1); // indeks jumlah buku yang
                                                                                         // dipinjam
                                book[5] = String.valueOf(Integer.parseInt(book[5]) - 1); // indeks stok
                                Date tanggalPeminjaman = new Date();
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(tanggalPengembalian);
                                calendar.add(Calendar.MINUTE, 1); // Tambahkan 1 menit untuk tanggal pengembalian
                                tanggalPengembalian = calendar.getTime();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                System.out.println("Tanggal Peminjaman\t: " + dateFormat.format(tanggalPeminjaman));
                                System.out.println("Tanggal Pengembalian\t: " + dateFormat.format(tanggalPengembalian));
                                System.out.println("Buku " + book[0] + " berhasil dipinjam.");
                                System.out.println("Judul buku: " + book[0] + ", " + "Kode buku: " + book[1] + ", "
                                        + "Stok: " + book[5]);
                            } else {
                                System.out
                                        .println("Stok buku " + book[0] + " tidak tersedia atau buku sedang dipinjam");
                                System.out.print("Apakah Anda ingin masuk antrian peminjaman? (ya/tidak): ");
                                String jawaban = scan.next();
                                if (jawaban.equalsIgnoreCase("ya")) {
                                    antrianPeminjaman.add(judulPinjam);
                                    riwayatAntrian.add(judulPinjam);
                                    System.out.println(
                                            "Anda telah masuk ke antrian peminjaman untuk buku " + judulPinjam);
                                }
                            }
                        }
                    }

                    if (!bukuTersedia) {
                        System.out.println("Maaf, buku dengan judul '" + judulPinjam + "' tidak ditemukan");
                    }
                    break;

                // Case ke-4 "Form Pengembalian Buku dan Denda"
                case 4:
                    if (isPetugas) {
                        System.out.println("\n==========FORM PENGEMBALIAN==========");
                        System.out.print("Masukkan judul buku yang ingin dikembalikan: ");
                        String judulKembali = scan.next();
                        for (int i = 0; i < dataBuku.size(); i++) {
                            String[] book = dataBuku.get(i);
                            if (judulKembali.equalsIgnoreCase(book[0])) {
                                bukuTersedia = true;
                                boolean statusPinjam = Boolean.parseBoolean(book[8]);
                                if (statusPinjam) {
                                    book[8] = "false";
                                    book[5] = String.valueOf(Integer.parseInt(book[5]) + 1);
                                    System.out.println("Buku " + book[0] + " berhasil dikembalikan.");
                                    System.out.println("Judul buku: " + book[0] + ", " + "Kode buku: " + book[1] + ", "
                                            + "Stok: " + book[5]);

                                    // Denda
                                    long selisihMenit = (new Date().getTime() - tanggalPengembalian.getTime())
                                            / (60 * 1000);
                                    if (selisihMenit > 0) {
                                        double denda = dendaPerMenit * selisihMenit;
                                        System.out.println("Denda yang harus dibayar: Rp. " + denda);
                                    }
                                } else {
                                    System.out.println("Buku " + book[0] + " tidak sedang dipinjam.");
                                    System.out.println("Judul buku: " + book[0] + ", " + "Kode buku: " + book[1] + ", "
                                            + "Stok: " + book[5]);
                                }
                            }
                        }

                        // Remove Antrian
                        for (String bukuAntrian : antrianPeminjaman) {
                            if (judulKembali.equalsIgnoreCase(bukuAntrian)) {
                                System.out.println("Buku " + judulKembali + " telah tersedia untuk dipinjam!");
                                antrianPeminjaman.remove(bukuAntrian);
                                break;
                            }
                        }

                        if (!bukuTersedia) {
                            System.out.println("Maaf, buku dengan judul '" + judulKembali + "' tidak ditemukan");

                        } else {
                            System.out.println("Maaf, Anda tidak memiliki akses untuk fitur ini.");
                        }
                    }
                    break;

                case 5:
                    System.out.println("\n==========RIWAYAT ANTRIAN PEMINJAMAN==========");
                    String[] arrayAntrian = new String[riwayatAntrian.size()];
                    riwayatAntrian.toArray(arrayAntrian);

                    for (int i = 0; i < arrayAntrian.length; i++) {
                        System.out.println((i + 1) + ". " + arrayAntrian[i]);
                    }
                    break;

                case 6:
                    if (isPetugas) {
                        System.out.println("\n==========HAPUS BUKU==========");
                        System.out.print("Masukkan judul buku yang ingin dihapus: ");
                        String judulHapus = scan.next();
                        boolean bukuDihapus = false;

                        for (int i = 0; i < dataBuku.size(); i++) {
                            String[] book = dataBuku.get(i);
                            if (judulHapus.equalsIgnoreCase(book[0])) {
                                dataBuku.remove(i);
                                bukuDihapus = true;
                                System.out.println("Buku dengan judul '" + judulHapus + "' berhasil dihapus.");
                                break;
                            }
                        }

                        if (!bukuDihapus) {
                            System.out.println("Buku dengan judul '" + judulHapus + "' tidak ditemukan.");
                        }
                    } else {
                        System.out.println("Maaf, Anda tidak memiliki akses untuk fitur ini.");
                    }
                    break;

                // Case ke-5 ini perintah logout/keluar
                case 7:
                    System.out.println("\nTerima kasih telah menggunakan sistem perpustakaan.");
                    scan.close();
                    System.exit(0);

                default:
                    System.out.println("\nPilihan tidak valid. Silakan pilih lagi.");
            }
        }
    }
}
