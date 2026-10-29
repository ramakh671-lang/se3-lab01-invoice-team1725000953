<div dir="rtl">

# Lab 01 — Invoice Refactoring

## الفريق: 1725000953

| الاسم الثلاثي | الرقم الجامعي | GitHub | الدور |
|---------------|---------------|--------|-------|
| عبادة خاروف | 1725001000 | @obada-kharouf | بناء النسخة القديمة |
| أحمد عايد العلي | 1721140727 | @AhmadAlali1212 | تحليل النسخة القديمة وحساب LCOM1 وتوثيقه في README |
| راما الخن | 1720069988 | @ramakh671-lang | تصميم الأصناف الأساسية وUML |
| أحمد الحجي | 1725000953 | @mdhj-git | واجهات التخزين والإرسال |
| سليمان صفيف | 1720041443 | @suliman_sfef | خدمة التنسيق والتحقق والتجميع |

## التشغيل

- النسخة القديمة: Run As ثم Java Application على `s1.lab1.Main`
- النسخة الجديدة: Run As ثم Java Application على `s1.lab2.Main`

## تحليل النسخة القديمة

الصنف المحلَّل: `s1.lab1.Invoice` (النسخة القديمة).

### 1. جدول المسؤوليات وأسباب التغيير

| # | المسؤولية | الدوال / الأعضاء | سبب التغيير (متى نضطر لتعديل الصنف؟) |
| --- | --- | --- | --- |
| 1 | حمل بيانات الفاتورة | الحقول `number`, `customerEmail`, `items` + `addItem()` + `getItems()` | تغيّر بنية الفاتورة، مثل إضافة حقل جديد (عملة، تاريخ، اسم الزبون) أو تغيير طريقة تمثيل البنود |
| 2 | الحساب | `calculateTotal()` | تغيّر قواعد التسعير: عتبة الخصم (1000)، نسبة الخصم (5%)، نسبة الضريبة (15%)، أو إضافة قواعد جديدة |
| 3 | الطباعة / التنسيق | `printInvoice()` | تغيّر شكل عرض الفاتورة (ترتيب الأسطر، عدد الخانات العشرية، لغة النص) أو طريقة الإخراج (شاشة أو ملف أو PDF) |
| 4 | الحفظ | `saveToDatabase(String type)` + القائمتان `DATABASE` و`FILES` | تغيّر وسيط التخزين أو صيغة السطر المحفوظ، أو إضافة نوع تخزين جديد (يتطلب تعديل `if/else`) |
| 5 | الإرسال بالبريد | `sendEmail()` | تغيّر قالب رسالة البريد أو آلية الإرسال الفعلية (SMTP مثلًا) |
| 6 | الإرسال بواتساب | `sendwhatsapp()` | تغيّر قالب رسالة واتساب أو إضافة رقم الهاتف أو تغيّر واجهة الإرسال |

**أسباب فصل هذه المسؤوليات:**

- الصنف لديه ستة أسباب مستقلة للتغيير، وهذا يخالف مبدأ المسؤولية الواحدة (SRP).
- الأسباب تأتي من جهات مختلفة: المالية للحساب، والتصميم للطباعة، وفريق البنية التحتية للتخزين والإرسال. تعديل أحدها قد يكسر الآخر.
- `sendEmail()` و`sendwhatsapp()` تستدعيان `printInvoice()` التي تطبع على الشاشة، فالإرسال مقترن بالطباعة (أثر جانبي غير مقصود).
- بناء نص الرسالة مكرر في `sendEmail()` و`sendwhatsapp()`.
- إضافة نوع تخزين أو قناة إرسال جديدة تفرض تعديل `Invoice` نفسه (انتهاك لمبدأ Open/Closed).
- القائمتان `DATABASE` و`FILES` حالة ساكنة مشتركة بين كل الفواتير (static)، فيصعب اختبار الصنف بمعزل عنها.

### 2. حساب LCOM1 للصنف القديم

**القاعدة:** LCOM1 = عدد أزواج الدوال التي لا تشترك في أي حقل كائن.

**ما استُبعد من الحساب (وفق قواعد الوظيفة):**

- الـ constructor `Invoice(int, String)`.
- دالة الوصول (getter) `getItems()`.
- الحقلان الساكنان `DATABASE` و`FILES`.

**حقول الكائن المعتمدة (3):** `number`, `customerEmail`, `items`.

**الدوال المحسوبة (6):** `addItem`, `calculateTotal`, `printInvoice`, `saveToDatabase`, `sendEmail`, `sendwhatsapp`.

عدد الأزواج = `C(6,2) = 15`.

**الحقول التي تستخدمها كل دالة مباشرة في جسمها (من الكود الفعلي):**

| الدالة | الحقول المستخدمة | ملاحظة من الكود |
| --- | --- | --- |
| `addItem` | `items` | `items.add(...)` |
| `calculateTotal` | `items` | حلقة على `items` |
| `printInvoice` | `number`, `items` | `number` في السطر الأول، و`items` في حلقة البنود |
| `saveToDatabase` | `number`, `customerEmail` | بناء السطر `number;customerEmail;total` |
| `sendEmail` | `number`, `customerEmail` | `To:` من `customerEmail`، و`Subject` من `number` |
| `sendwhatsapp` | `number` | `To:` نص ثابت، فلا تستخدم `customerEmail` |

**جدول الأزواج الكامل:**

| # | الزوج | حقول الأولى | حقول الثانية | الحقول المشتركة | يُحسب في LCOM1؟ |
| --- | --- | --- | --- | --- | --- |
| 1 | `addItem` – `calculateTotal` | items | items | items | لا |
| 2 | `addItem` – `printInvoice` | items | number, items | items | لا |
| 3 | `addItem` – `saveToDatabase` | items | number, customerEmail | لا شيء | **نعم** |
| 4 | `addItem` – `sendEmail` | items | number, customerEmail | لا شيء | **نعم** |
| 5 | `addItem` – `sendwhatsapp` | items | number | لا شيء | **نعم** |
| 6 | `calculateTotal` – `printInvoice` | items | number, items | items | لا |
| 7 | `calculateTotal` – `saveToDatabase` | items | number, customerEmail | لا شيء | **نعم** |
| 8 | `calculateTotal` – `sendEmail` | items | number, customerEmail | لا شيء | **نعم** |
| 9 | `calculateTotal` – `sendwhatsapp` | items | number | لا شيء | **نعم** |
| 10 | `printInvoice` – `saveToDatabase` | number, items | number, customerEmail | number | لا |
| 11 | `printInvoice` – `sendEmail` | number, items | number, customerEmail | number | لا |
| 12 | `printInvoice` – `sendwhatsapp` | number, items | number | number | لا |
| 13 | `saveToDatabase` – `sendEmail` | number, customerEmail | number, customerEmail | number, customerEmail | لا |
| 14 | `saveToDatabase` – `sendwhatsapp` | number, customerEmail | number | number | لا |
| 15 | `sendEmail` – `sendwhatsapp` | number, customerEmail | number | number | لا |

**النتيجة:**

- أزواج لا تشترك في أي حقل: 6 (الأزواج 3 و4 و5 و7 و8 و9).
- أزواج تشترك في حقل واحد على الأقل: 9.
- **LCOM1 (النسخة القديمة) = 6**

**قراءة النتيجة:** الدالتان `addItem` و`calculateTotal` تعملان على `items` فقط، والدوال الأربع الأخرى تدور حول `number` و`customerEmail`، فالصنف منقسم إلى مجموعتين شبه منفصلتين، وهذا دليل رقمي على ضعف التماسك. كما أن `printInvoice` هي الحلقة الوحيدة التي تربط المجموعتين.

**افتراض الحساب:** تُحتسب الحقول التي تصل إليها الدالة مباشرة في جسمها فقط، ولا تُحتسب الحقول التي تصل إليها عبر استدعاء دالة أخرى (مثل استدعاء `printInvoice()` داخل `sendEmail()`)، وهذا ما تنص عليه قاعدة «لا تشترك في أي حقل».

## التصميم الجديد

- مخطط UML قبل: docs/uml-before.png
- مخطط UML بعد: docs/uml-after.png

| الصنف | مسؤوليته | LCOM1 |
| --- | --- | --- |
| `Invoice` | حمل بيانات الفاتورة | 0 (دالة واحدة محسوبة: `addItem`، فلا أزواج) |
| `InvoiceItem` | حمل بيانات البند | 0 (لا دوال محسوبة، getters فقط) |
| `InvoiceCalculator` | حساب المجموع | لا ينطبق (لا حقول كائن) |
| `InvoiceFormatter` | تنسيق نص الفاتورة | لا ينطبق (لا حقول كائن) |
| `InvoiceStorage` | واجهة التخزين | لا ينطبق (Interface) |
| `InvoiceSender` | واجهة الإرسال | لا ينطبق (Interface) |
| `DatabaseStorage` | الحفظ في قائمة قاعدة البيانات | لا ينطبق (`DATABASE` حقل ساكن) |
| `FileStorage` | الحفظ في قائمة الملفات | لا ينطبق (`FILES` حقل ساكن) |
| `EmailSender` | الإرسال بالبريد | لا ينطبق (لا حقول كائن) |
| `WhatsAppSender` | الإرسال بواتساب | لا ينطبق (لا حقول كائن) |
| `InvoiceService` | تنسيق الخطوات | 2 |

### مقارنة LCOM1 قبل وبعد

- قبل: صنف واحد `Invoice` بقيمة **6**، وستة أسباب للتغيير.
- بعد: أعلى قيمة **2** في `InvoiceService`، وبقية الأصناف 0 أو لا ينطبق.
- القيمة 2 في `InvoiceService` طبيعية: فهو صنف تنسيق تعتمد كل دالة فيه على اعتمادية مختلفة، وكل اعتمادية خلف واجهة أو صنف مستقل. الانخفاض من 6 إلى 2 مع تقسيم المسؤوليات إلى أصناف مستقلة يدل على تحسن التماسك.
- افتراض الحساب: الحقول التي تصل إليها الدالة مباشرة في جسمها فقط، دون الحقول التي تصل إليها عبر استدعاء دالة أخرى.

## التحقق

انظر الملف `docs/verification.pdf`: لقطتا خرج النسختين `s1.lab1.Main` و`s1.lab2.Main` وهما متطابقتان حرفيًا.

</div>