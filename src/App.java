import java.util.*;
import java.text.*;

public class App {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Deklarasi dan Inisialisasi Variabel
        String[] namaMhs = { "Indira", "Luqman", "Hayyin" };
        String[] usernameMhs = { "2341720001", "2341720077", "2341720226" };
        String[] passwordMhs = { "123", "asd", "asd" };
        String namaMahasiswa = "";
        String nimMhs = "";
        boolean login = false;

        String[][] dataBuku = {
                { "C#", "A001", "Indira", "2020", "Explorer", "1", "0", null, "false" },
                { "Java", "A002", "Nafa", "2022", "ex", "5", "0", null, "false" }
        };

        int dendaPerMenit = 10000;
        Date tanggalPengembalian = new Date();

        System.out.println("SISTEM PERPUSTAKAAN");

        // Login
        while (!login) {
            System.out.print("\nMasukkan Username anda: ");
            String username = scan.next();
            System.out.print("Masukkan Password anda: ");
            String password = scan.next();
            for (int i = 0; i < usernameMhs.length; i++) {
                if (username.equals(usernameMhs[i]) && password.equals(passwordMhs[i])) {
                    System.out.println("Login berhasil. Selamat datang, " + namaMhs[i] + "!");
                    namaMahasiswa = namaMhs[i];
                    nimMhs = usernameMhs[i];
                    login = true;
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
            System.out.println("1. Tampilkan Daftar Buku");
            System.out.println("2. Tambah Data Buku");
            System.out.println("3. Pinjam Buku");
            System.out.println("4. Kembalikan Buku");
            System.out.println("5. Tampilkan Informasi Buku");
            System.out.println("6. Keluar");
            System.out.print("\nPilih menu (1/2/3/4/5/6): ");

            int perintah = scan.nextInt();

            switch (perintah) {
                case 1:
                    System.out.println("\n==========DAFTAR BUKU==========");
                    for (int i = 0; i < dataBuku.length; i++) {
                        System.out.println((i + 1) + ". " + dataBuku[i][0]);
                    }
                    break;

                // Case ke-2 "Tambah Buku"
                case 2:
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

                    System.out.println("Judul buku: " + title + ", " + "Kode buku: " + code + ", "
                            + "Nama pengarang: " + author + ", " + "Tahun terbit: " + year + ", "
                            + "Nama penerbit: " + publisher + ", " + "Stok: " + stock + " Berhasil ditambahkan");
                    break;

                // Case ke-3 (Form Peminjaman Buku)
                case 3:
                    System.out.println("\n==========FORM PEMINJAMAN==========");
                    System.out.println("Nama Mahasiswa\t: " + namaMahasiswa);
                    System.out.println("NIM\t\t: " + nimMhs);
                    System.out.print("\nMasukkan judul buku yang ingin dipinjam: ");
                    scan.nextLine();
                    String judulPinjam = scan.nextLine();
                    for (int i = 0; i < dataBuku.length; i++) {
                        if (judulPinjam.equalsIgnoreCase(dataBuku[i][0])) {
                            boolean statusPinjam = Boolean.parseBoolean(dataBuku[i][8]);
                            if (Integer.parseInt(dataBuku[i][5]) > 0 && !statusPinjam) {
                                dataBuku[i][8] = "true";
                                dataBuku[i][6] = String.valueOf(Integer.parseInt(dataBuku[i][6]) + 1);
                                dataBuku[i][5] = String.valueOf(Integer.parseInt(dataBuku[i][5]) - 1);
                                Date tanggalPeminjaman = new Date();
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(tanggalPengembalian);
                                calendar.add(Calendar.MINUTE, 1); // Tambahkan 1 menit untuk tanggal pengembalian
                                tanggalPengembalian = calendar.getTime();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                System.out.println("Tanggal Peminjaman\t: " + dateFormat.format(tanggalPeminjaman));
                                System.out.println("Tanggal Pengembalian\t: " + dateFormat.format(tanggalPengembalian));
                                System.out.println("Buku " + dataBuku[i][0] + " berhasil dipinjam.");
                                System.out.println(
                                        "Judul buku: " + dataBuku[i][0] + ", " + "Kode buku: " + dataBuku[i][1] + ", "
                                                + "Stok: " + dataBuku[i][5]);
                            } else {
                                System.out.println(
                                        "Stok buku " + dataBuku[i][0] + " tidak tersedia atau buku sedang dipinjam");
                            }
                        }
                    }
                    break;

                // Case ke-4 "Form Pengembalian Buku dan Denda"
                case 4:
                    System.out.println("\n==========FORM PENGEMBALIAN==========");
                    System.out.print("Masukkan judul buku yang ingin dikembalikan: ");
                    String judulKembali = scan.next();
                    for (int i = 0; i < dataBuku.length; i++) {
                        if (judulKembali.equalsIgnoreCase(dataBuku[i][0])) {
                            boolean statusPinjam = Boolean.parseBoolean(dataBuku[i][8]);
                            if (statusPinjam) {
                                dataBuku[i][8] = "false";
                                dataBuku[i][5] = String.valueOf(Integer.parseInt(dataBuku[i][5]) + 1);
                                System.out.println("Buku " + dataBuku[i][0] + " berhasil dikembalikan.");
                                System.out.println(
                                        "Judul buku: " + dataBuku[i][0] + ", " + "Kode buku: " + dataBuku[i][1] + ", "
                                                + "Stok: " + dataBuku[i][5]);
                                long selisihMenit = (new Date().getTime() - tanggalPengembalian.getTime())
                                        / (60 * 1000);
                                if (selisihMenit > 0) {
                                    double denda = dendaPerMenit * selisihMenit;
                                    System.out.println("Denda yang harus dibayar: Rp. " + denda);
                                }
                            } else {
                                System.out.println("Buku " + dataBuku[i][0] + "Berhasil dipinjam");
                            }

                        }
                    }
                    break;

                // Case ke-5 "Informasi Buku" meliputi data buku dan status buku

                // Case ke-5 "Informasi Buku" meliputi data buku dan status buku
                case 5:
                    System.out.println("\n==========INFORMASI BUKU==========");
                    for (int i = 0; i < dataBuku.length; i++) {
                        System.out.println((i + 1) + ". " + "Judul buku: " + dataBuku[i][0] + ", " + "Kode buku: "
                                + dataBuku[i][1] + ", "
                                + "Nama pengarang: " + dataBuku[i][2] + ", " + "Tahun terbit: " + dataBuku[i][3] + ", "
                                + "Nama penerbit: " + dataBuku[i][4] + ", " + "Stok: " + dataBuku[i][5]);
                        boolean statusPinjam = Boolean.parseBoolean(dataBuku[i][8]);
                        if (statusPinjam) {
                            System.out.println(" (Buku Sedang Dipinjam) ");
                        } else {
                            System.out.println(" (Buku Tersedia) ");
                        }
                    }
                    break;

                // Case ke-6 ini perintah logout/keluar
                case 6:
                    System.out.println("\nTerima kasih telah menggunakan sistem perpustakaan.");
                    type = false;
                    break;

                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih lagi.");
            }
        }
        scan.close();
    }
}