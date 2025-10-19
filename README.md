🖥️ مشروع Dashboard بالجافا (Java Swing + FlatLaf)

المشروع ده معمول باستخدام Java Swing، والـ FlatLaf اللي هو شكل جاهز (Look and Feel) بيخلي شكل البرنامج شيك وحديث، زي التطبيقات الجديدة اللي فيها Light/Dark mode.

📚 المكتبات (Libraries) المستخدمة

في شوية مكتبات لازم تضيفها علشان المشروع يشتغل:

flatlaf-3.2.jar → الأساس بتاع الـ FlatLaf (الستايل العام)

flatlaf-extras-3.2.jar → فيها شوية إضافات وأدوات خاصة بالـ FlatLaf

jsvg-1.2.0.jar → علشان تقدر تستخدم صور SVG (أيقونات بجودة عالية)

flatlaf-fonts-roboto-2.137.jar → خط Roboto اللي بيستخدمه الواجهة

swing-toast-notifications-1.0.1.jar → علشان تعمل إشعارات (Toast) زي اللي بتطلع في الموبايل

🧩 إزاي تشغّل الفورم (Form)
// الكلاس ده موجود في الباكدج raven.application
// بياخد كـ parameter مكون (Component)
Application.showForm(new PanelForm());

// لو عايز تحدد المينيو اللي يبقى متعلم عليه
Application.setSelectedMenu(0, 0);


يعني ببساطة كده، السطر الأول بيعرض الفورم اللي انت عايزه (زي الصفحة أو الشاشة).
والسطر التاني بيخلي المينيو يختار عنصر معين بالـ index بتاعه.

📋 المينيو (Menu Items)

الكود ده بيتحط في raven.menu.Menu.java

private final String menuItems[][] = {
    {"~MAIN~"}, // ده عنوان المينيو
    {"Dashboard"}, // أول اختيار
    {"Email", "Inbox", "Read", "Compost"}, // مينيو فرعي فيه 3 اختيارات
};


✅ خُد بالك:
لو حطيت اسم العنوان بين ~ زي كده ~MAIN~ يبقى ده عنوان للمجموعة مش زرار بيتضغط عليه.

⚙️ أحداث المينيو (Menu Events)

الكود ده بيحدد إيه اللي يحصل لما المستخدم يضغط على عنصر من المينيو:

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

index هو ترتيب المينيو الرئيسي (زي Dashboard أو Email).

subIndex هو ترتيب العنصر اللي جواه (زي Inbox، Read...).

Application.showForm(...) بيعرض الصفحة اللي انت عايزها.

action.cancel() بتمنع أي أكشن لو مش عايز تعمل حاجة.

🎨 تخصيص الشكل (Customization)

لو حبيت تغير في شكل الواجهة (ألوان – خطوط – حجم – أنيميشن... إلخ)
تقدر تستخدم خصائص الـ FlatLaf من هنا:

صفحة GitHub

الـ Documentation الرسمي

📸 صور من الداشبورد

في المشروع فيه صور بتوضح الشكل في Light و Dark mode
زي كده 👇
(في اللينكات الأصلية صور واجهة جميلة جدًا للـ Dashboard)

🆕 تحديثات المشروع (Update Notes)

📅 آخر التحديثات اللي حصلت:

التاريخ	التحديث
27-05-2023	إضافة خاصية العناوين في المينيو (~TITLE~)
28-05-2023	تحسين طريقة عرض الفورم
29-05-2023	تعديل المينيو البوب أب (Popup) وشكله
31-05-2023	إضافة شاشة تسجيل الدخول
17-06-2023	إضافة إشعارات Toast Notifications
27-06-2023	دعم الخطوط المخصصة في المينيو
27-06-2023	دعم الـ Right-To-Left (زي اللغة العربية)
03-10-2023	إضافة خاصية AccentControl.show لإظهار/إخفاء شريط الألوان
💡 الخلاصة

المشروع ده معمول علشان يعمل Dashboard احترافية بجافا Swing،
ويكون ليها Light/Dark mode، ومينيو متحرك، وفورمات سهلة في الاستخدام.
كله مبني على FlatLaf اللي بيخلي شكل البرنامج عصري جدًا من غير ما تتعب نفسك في التصميم.
