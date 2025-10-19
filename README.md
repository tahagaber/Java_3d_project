# 🖥️ مشروع Dashboard بالجافا (Java Swing + FlatLaf)

المشروع ده معمول باستخدام **Java Swing**، ومعاه **FlatLaf Look and Feel** علشان يخلي شكل البرنامج شيك وحديث، وفيه **Light Mode** و **Dark Mode**.

---

## 📚 المكتبات (Libraries) المستخدمة

علشان المشروع يشتغل، ضيف المكتبات دي في مجلد `libs`:

- `flatlaf-3.2.jar`
- `flatlaf-extras-3.2.jar`
- `jsvg-1.2.0.jar`
- `flatlaf-fonts-roboto-2.137.jar`
- `swing-toast-notifications-1.0.1.jar`

---

## 🚀 مثال لتشغيل الفورم

```java
//  Application class من الباكدج raven.application
//  بياخد كـ parameter مكون (Component)

Application.showForm(new PanelForm());

//  لو عايز تحدد المينيو اللي يبقى متعلم عليه
Application.setSelectedMenu(0, 0);
private final String menuItems[][] = {
    {"~MAIN~"}, //  عنوان المينيو
    {"Dashboard"},
    {"Email", "Inbox", "Read", "Compost"},
};


⚙️ أحداث المينيو (Menu Events)

الكود اللي بيحدد إيه اللي يحصل لما المستخدم يختار عنصر من المينيو:

menu.addMenuEvent(new MenuEvent() {
    @Override
    public void menuSelected(int index, int subIndex, MenuAction action) {
        if (index == 1) {
            if (subIndex == 1) {
                Application.showForm(new FormInbox());
            } else if (subIndex == 2) {
                Application.showForm(new FormRead());
            }
        } else {
            action.cancel();
        }
    }
});


📌 التوضيح:

index هو ترتيب المينيو الرئيسي.

subIndex هو ترتيب العنصر اللي جواه.

Application.showForm(...) بيعرض الصفحة اللي انت عايزها.

action.cancel() بتمنع تنفيذ أي حاجة لو مش مطلوب حاجة تتعمل.

🎨 تخصيص الشكل (Customization)

تقدر تغير الألوان، الخطوط، والحركات باستخدام خصائص FlatLaf من الروابط دي:

🔗 صفحة GitHub الرسمية لـ FlatLaf

📘 الـ Documentation الرسمي

📸 صور من الداشبورد
<img src="https://github.com/DJ-Raven/flatlaf-dashboard/assets/58245926/23ab0477-c11e-498d-92f9-37f6bfa944f6" width="350"/> <img src="https://github.com/DJ-Raven/flatlaf-dashboard/assets/58245926/44d1972b-b29b-4a11-8fdd-be7f27782e5b" width="350"/> </br> <img src="https://github.com/DJ-Raven/flatlaf-dashboard/assets/58245926/71c03d69-4508-43ea-86e6-2cba0c8e1dc8" width="350"/> <img src="https://github.com/DJ-Raven/flatlaf-dashboard/assets/58245926/fe793459-33b8-47e7-be06-385c3e4dfa37" width="350"/>
🆕 تحديثات المشروع (Update Notes)
التاريخ	التحديث
27-05-2023	إضافة خاصية العناوين في المينيو باستخدام ~
28-05-2023	تحسين طريقة عرض الفورم
29-05-2023	تعديل شكل الـ Popup Menu وإضافة Drop Shadow
31-05-2023	إضافة شاشة تسجيل الدخول
17-06-2023	إضافة إشعارات Toast Notifications
27-06-2023	دعم الخطوط المخصصة في المينيو
27-06-2023	دعم الاتجاه من اليمين لليسار (Right-To-Left)
03-10-2023	إضافة خاصية AccentControl.show لإظهار/إخفاء شريط الألوان
