# Üniversite Kütüphane Yönetim Sistemi
### Destek olan kişi : Abdulkadir Kılıç (https://github.com/Abdulkadirkilicc)
---

Bu proje, bir üniversite kütüphanesinin işleyişini yönetmek için geliştirilmiş bir *Kütüphane Yönetim Sistemi* yazılımıdır. Kullanıcılar kitap arayabilir, ödünç alabilir, iade edebilir ve yöneticiler kütüphane envanterini yönetebilir.

---


## *Projenin Özellikleri*

### *Genel Özellikler*
- Kullanıcılar (öğrenciler ve yöneticiler) için giriş ve kayıt işlemleri.
- Kitapların ödünç alınması, iade edilmesi ve durumlarının takibi.
- Kitap adına, yazara ve yayınevine göre arama işlemleri.
- Yönetici paneli üzerinden kitap ve kullanıcı yönetimi.

### *Kullanılan Teknolojiler*
- *Java*: Uygulama geliştirme.
- *Postgre*: Veritabanı yönetimi.
- *Swing*: Grafiksel kullanıcı arayüzü (GUI).

### *Tasarım Desenleri*
1. *Singleton Pattern*: Veritabanı bağlantısı (DBConnection) ve oturum yönetimi (UserSession).
2. *Factory Pattern*: Kullanıcı türlerine göre nesne oluşturma (UserFactory).
3. *State Pattern*: Kitapların durum yönetimi (AvailableState, OutOfStock).
4. *Builder Pattern*: Kitap nesnesi oluşturma (Book sınıfında).
5. *Observer Pattern*: Kitap durumu değiştiğinde kullanıcıya bildirim gönderme.

---

## *Kurulum ve Çalıştırma*

### *Gereksinimler*
- *Java 8* veya üzeri
- Postgre*
- *Maven* (isteğe bağlı)

### *Adımlar*
1. *Projenin Klonlanması*
   bash
   git clone https://github.com/AdiDayat12/universite-kutuphane-yonetim-sistemi.git
   cd universite-kutuphane-yonetim-sistemi
   

2. *MySQL Veritabanı Kurulumu*
   - MySQL'de aşağıdaki komutları çalıştırarak veritabanını oluşturun:
     sql
     CREATE DATABASE library_management_system;
     USE library_management_system;

     -- Tablo oluşturma sorguları için `database_schema_mysql` dosyasını kullanın.
     

3. *Projenin Derlenmesi ve Çalıştırılması*
   - Eğer bir IDE kullanıyorsanız (IntelliJ IDEA, Eclipse):
     1. Projeyi açın.
     2. Ana sınıfı (Main.java) çalıştırın.

   - Eğer terminal kullanıyorsanız:
     bash
     javac -d out src/com/grup31/universite_kutuphane_yonetim_sistemi/Main.java
     java -cp out com.grup31.universite_kutuphane_yonetim_sistemi.Main
     

---

## *Projenin Özellikleri (Detaylı)*

### *Giriş ve Kayıt İşlemleri*
- Kullanıcılar (öğrenci ve yönetici) giriş yapabilir veya sisteme kayıt olabilir.
- Kullanıcı türlerine göre yetkilendirme yapılmıştır.

### *Kitap Yönetimi*
- *Yönetici*:
  - Kitap ekleyebilir, güncelleyebilir ve silebilir.
- *Öğrenci*:
  - Kitap arayabilir, ödünç alabilir ve iade edebilir.

### *Raporlama*
- Yönetici, ödünç alınan kitaplarla ilgili raporları görüntüleyebilir.

---

## *Katkıda Bulunanlar*
