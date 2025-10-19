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

