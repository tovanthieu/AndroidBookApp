package com.example.nhomsundayqlsach;
/*
 * Bản quyền (c) 2024 Tô Văn Thiều
 *
 * Đoạn mã này là sở hữu của Tô Văn Thiều.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Book.db";
    private static final int DATABASE_VERSION = 2;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Category
        db.execSQL("CREATE TABLE Category (" +
                "CategoryID INTEGER PRIMARY KEY," +
                "CategoryName TEXT NOT NULL" +
                ");");

        // BẢNG Book
        db.execSQL("CREATE TABLE Book (" +
                "BookID INTEGER PRIMARY KEY," +
                "CategoryID INTEGER," +
                "Title TEXT NOT NULL," +
                "Price REAL," +
                "Description TEXT," +
                "Content TEXT," +
                "ImageName TEXT," +
                "FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID)" +
                ");");



        // Tạo bảng Role
        db.execSQL("CREATE TABLE Role (" +
                "RoleID INTEGER PRIMARY KEY," +
                "RoleName TEXT NOT NULL" +
                ");");

        // Tạo bảng Users
        db.execSQL("CREATE TABLE Users (" +
                "UserID INTEGER PRIMARY KEY," +
                "Username TEXT NOT NULL UNIQUE," +
                "Password TEXT NOT NULL," +
                "RoleID INTEGER," +
                "FOREIGN KEY (RoleID) REFERENCES Role(RoleID)" +
                ");");

        // Tạo bảng Orders
        db.execSQL("CREATE TABLE Orders (" +
                "OrderID INTEGER PRIMARY KEY," +
                "UserID INTEGER," +
                "OrderDate DATE," +
                "Status TEXT DEFAULT 'Chưa thanh toán'," +
                "FOREIGN KEY (UserID) REFERENCES Users(UserID)" +
                ");");

        // Tạo bảng OrderDetails
        db.execSQL("CREATE TABLE OrderDetails (" +
                "OrderDetailID INTEGER PRIMARY KEY," +
                "OrderID INTEGER," +
                "BookID INTEGER," +
                "Price REAL," +
                "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)," +
                "FOREIGN KEY (BookID) REFERENCES Book(BookID)" +
                ");");
        db.execSQL("INSERT INTO Category (CategoryID, CategoryName) VALUES (1, 'Tình Cảm');");
        db.execSQL("INSERT INTO Category (CategoryID, CategoryName) VALUES (2, 'Khoa Học');");
        db.execSQL("INSERT INTO Category (CategoryID, CategoryName) VALUES (3, 'Truyện Ngắn');");

        db.execSQL("INSERT INTO Role (RoleID, RoleName) VALUES (1, 'admin');");
        db.execSQL("INSERT INTO Role (RoleID, RoleName) VALUES (2, 'user');");

        // Thêm tài khoản admin vào bảng Users
        db.execSQL("INSERT INTO Users (UserID, Username, Password, RoleID) VALUES (1, 'admin', 'admin', 1);");
        db.execSQL("INSERT INTO Users (UserID, Username, Password, RoleID) VALUES (2, 'thieuto', '123456', 2);");



        // Cuộc Đời và Vũ Trụ
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (2, 'Cuộc Đời và Vũ Trụ', 150000, 'Khám phá sự kỳ diệu của cuộc sống và bí ẩn của vũ trụ trong cuốn sách này.', 'Chương I\n" +
                "\n" +
                "NGƯỜI LƯỚT CÙNG TIA SÁNG\n" +
                "\n" +
                "ôi đảm bảo sẽ có bốn bài báo khoa học”, giám định viên\n" +
                "bằng sáng chế trẻ tuổi Einstein viết cho bạn mình. Bức\n" +
                "thư này hóa ra lại mang một số tin tức trọng đại nhất\n" +
                "trong lịch sử khoa học, nhưng tính chất quan trọng của nó bị ẩn\n" +
                "đi bằng giọng điệu tinh nghịch đặc trưng của tác giả. Rõ ràng là\n" +
                "ngay trước đó ông đã gọi bạn mình là “cá voi ướp lạnh”, và xin lỗi\n" +
                "vì viết một bức thư “huyên thuyên không đáng bận tâm”. Chỉ khi\n" +
                "tìm được thời gian để mô tả những bài báo mà mình đã viết trong\n" +
                "thời gian rảnh, ông mới cho thấy dấu hiệu nào đó là ông đã cảm\n" +
                "nhận được tầm quan trọng của chúng.\n" +
                "“Bài báo đầu tiên bàn về bức xạ và các tính chất năng lượng của\n" +
                "ánh sáng và có tính cách mạng rất cao,” ông giải thích. Đúng vậy,\n" +
                "nó mang tính cách mạng. Nó lập luận rằng ánh sáng không chỉ có\n" +
                "thể được xem như là sóng, mà còn như là một dòng các hạt li ti\n" +
                "được gọi là lượng tử. Những hệ quả vốn cuối cùng sẽ nảy sinh từ\n" +
                "lý thuyết này – một vũ trụ không có tính nhân quả chặt chẽ hay\n" +
                "tính tất định – đã ám ảnh ông đến cuối đời.\n" +
                "“Bài báo thứ hai viết về sự xác định kích thước thật của nguyên\n" +
                "tử.” Mặc dù chính sự tồn tại của nguyên tử vẫn gây nhiều tranh\n" +
                "cãi, nhưng nó lại là thứ đơn giản nhất trong các bài báo, đó là lý\n" +
                "do ông đã chọn nó là phần an toàn nhất cho nỗ lực mới nhất xin\n" +
                "làm luận án tiến sỹ. Lúc đó, ông đang trong quá trình làm một\n" +
                "cuộc cách mạng cho ngành vật lý, nhưng ông liên tiếp vấp phải\n" +
                "khó khăn trong nỗ lực tìm được một công việc học thuật hoặc\n" +
                "thậm chí lấy bằng tiến sỹ, những gì mà ông hy vọng có thể giúp\n" +
                "mình thăng tiến từ nhân viên kiểm định bậc ba lên nhân viên\n" +
                "kiểm định bậc hai tại Cục Cấp bằng Sáng chế.\n" +
                "\n" +
                "Bài báo thứ ba giải thích chuyển động hỗn loạn của các hạt vi mô\n" +
                "trong chất lỏng bằng cách sử dụng phân tích thống kê cho va\n" +
                "chạm ngẫu nhiên. Qua đó đã chứng minh được rằng nguyên tử và\n" +
                "phân tử thật sự tồn tại.\n" +
                "“Bài báo thứ tư giờ mới chỉ là một bản phác thảo thô, nó đề cập tới\n" +
                "điện động lực học của vật chuyển động khi điều chỉnh lý thuyết\n" +
                "về không gian và thời gian.” Đấy, điều đó chắc chắn còn hơn cả\n" +
                "nói huyên thuyên. Thuần túy dựa trên thí nghiệm tưởng tượng –\n" +
                "được thực hiện trong đầu ông chứ không phải trong phòng thí\n" +
                "nghiệm – ông đã quyết định gạt bỏ khái niệm về thời gian và\n" +
                "không gian tuyệt đối của Newton. Sau này, nó được biết đến với\n" +
                "cái tên Thuyết Tương đối hẹp.\n" +
                "Điều ông không nói với bạn mình, vì khi đó ông chưa nhận ra, là\n" +
                "ông sẽ viết bài báo thứ năm cũng trong năm đó, một bài bổ sung\n" +
                "ngắn cho bài báo thứ tư – khẳng định mối liên hệ giữa năng lượng\n" +
                "và khối lượng. Từ đây, phương trình nổi tiếng nhất trong vật lý:\n" +
                "E=mc2 đã ra đời.\n" +
                "Khi nhìn lại thế kỷ sẽ được nhớ đến vì sẵn sàng phá bỏ những mối\n" +
                "liên hệ (của vật lý học) cổ điển và hướng tới một kỷ nguyên luôn\n" +
                "tìm cách nuôi dưỡng tính sáng tạo cần thiết cho sự đổi mới khoa\n" +
                "học, ta sẽ thấy một nhân vật nổi bật như là biểu tượng hàng đầu\n" +
                "trong thời đại của chúng ta: một người tị nạn, tốt bụng, phải chạy\n" +
                "trốn áp bức, mái tóc xù như vầng hào quang, đôi mắt sáng long\n" +
                "lanh, lòng nhân ái dễ mến và trí tuệ xuất chúng, những điều đó\n" +
                "làm gương mặt của ông trở thành một biểu tượng và làm tên ông\n" +
                "trở thành một từ đồng nghĩa với thiên tài. Albert Einstein là một\n" +
                "“người thợ khóa” được phú cho trí tưởng tượng tuyệt vời, và được\n" +
                "dẫn dắt bởi niềm tin vào sự hài hòa trong tạo phẩm của tự nhiên.\n" +
                "Câu chuyện hấp dẫn về ông, một bằng chứng cho sự kết nối giữa\n" +
                "tính sáng tạo và sự tự do, phản ánh những thành tựu và những\n" +
                "náo động của kỷ nguyên hiện đại.\n" +
                "Giờ đây, khi những tư liệu lưu trữ của Einstein đã được công khai\n" +
                "hoàn toàn, ta có thể khám phá khía cạnh riêng tư – tính cách\n" +
                "không chịu theo lề thói, bản năng nổi loạn, tính tò mò, những\n" +
                "niềm đam mê và thái độ bàng quan của ông – đã hòa quyện với\n" +
                "\n" +
                "khía cạnh chính trị và khía cạnh khoa học trong ông như thế nào.\n" +
                "Hiểu người đàn ông này giúp ta hiểu ngọn nguồn khoa học trong\n" +
                "ông và ngược lại. Tính cách, trí tưởng tượng và thiên tài sáng tạo,\n" +
                "tất cả liên quan với nhau như là những thành phần của một\n" +
                "trường thống nhất nào đó.\n" +
                "Mặc dù có tiếng là người lạnh lùng, nhưng thực ra ông là người\n" +
                "nồng nhiệt trong nghiên cứu khoa học lẫn trong đời tư. Ở đại học,\n" +
                "ông yêu say đắm thiếu nữ duy nhất trong lớp vật lý của mình,\n" +
                "một người Serbia nghiêm túc với mái tóc sẫm màu tên là Mileva\n" +
                "Marić. Họ có một người con gái lúc chưa kết hôn, sau đó họ cưới\n" +
                "nhau và có thêm hai người con trai. Bà là người lắng nghe các ý\n" +
                "tưởng khoa học của ông và giúp kiểm tra phần toán học trong các\n" +
                "bài báo. Thế nhưng mối quan hệ của họ cuối cùng đã đổ vỡ.\n" +
                "Einstein đưa ra một thỏa thuận. Ông nói, một ngày nào đó, mình\n" +
                "sẽ được trao giải Nobel; nếu bà chấp nhận ly dị, thì ông sẽ chuyển\n" +
                "toàn bộ số tiền thưởng đó cho bà. Bà suy nghĩ một tuần rồi đồng\n" +
                "ý. Vì các thuyếtcủa ông quá đỗi cấp tiến, nên phải sau 17 năm kể\n" +
                "từ khi có nhiều ý tưởng kỳ diệu ở Cục Cấp bằng Sáng chế, ông mới\n" +
                "được trao giải thưởng này. Đến lúc đó bà mới nhận được tiền từ\n" +
                "ông.\n" +
                "Cuộc đời và công trình của Einstein phản ánh sự đổ vỡ của những\n" +
                "xác tín xã hội và luân lý đạo đức tuyệt đối trong không khí của\n" +
                "chủ nghĩa hiện đại đầu thế kỷ XX. Sự nổi loạn đầy tính sáng tạo\n" +
                "lan tỏa khắp nơi: Picasso6, Joyce7, Freud, Stravinsky8,\n" +
                "Schoenberg9 và nhiều người khác đang phá bỏ những mối liên hệ\n" +
                "truyền thống. Tiếp thêm vào bầu không khí này là quan niệm về\n" +
                "vũ trụ theo đó thời gian,và không gian và các tính chất của các\n" +
                "hạt dường như dựa trên những thay đổi thất thường của các quan\n" +
                "sát.\n" +
                "Tuy nhiên, Einstein không hẳn là người theo chủ nghĩa tương\n" +
                "đối10 mặc dù nhiều người cho rằng ông thuộc nhóm này, trong\n" +
                "đó có một số người mà thái độ coi thường ông có lẫn chủ nghĩa\n" +
                "bài Do Thái. Bên dưới tất cả các lý thuyết của ông, bao gồm cả\n" +
                "Thuyết Tương đối, là cuộc tìm kiếm những cái bất biến, cái chắc\n" +
                "chắn và cái tuyệt đối. Einstein cảm thấy có một thực tại hài hòa\n" +
                "làm nền tảng cho các quy luật của vũ trụ, và mục đích của khoa\n" +
                "\n" +
                "học là khám phá ra nó.\n" +
                "Ông bắt đầu cuộc tìm kiếm của mình năm 1895 khi 16 tuổi và đã\n" +
                "hình dung sẽ thế nào khi lướt cùng tia sáng. Năm diệu kỳ của ông\n" +
                "đến sau đó mười năm, như trong bức thư trên đã mô tả, nó đã đặt\n" +
                "nền móng cho hai bước tiến vĩ đại của vật lý thế kỷ XX: Thuyết\n" +
                "Tương đối và lý thuyết lượng tử.\n" +
                "Năm 1915, một thập kỷ sau, ông mới có được thành tựu lớn nhất\n" +
                "của mình về tự nhiên, đó là Thuyết Tương đối rộng, một trong\n" +
                "những lý thuyết đẹp đẽ nhất trong toàn bộ nền khoa học. Cũng\n" +
                "như với Thuyết Tương đối hẹp, tư duy của ông đã phát triển qua\n" +
                "các thí nghiệm tưởng tượng. Ông đã phỏng đoán một trong các\n" +
                "thí nghiệm này khi tưởng tượng mình đứng trong một thang máy\n" +
                "đóng kín đang tăng tốc trong không gian. Ta không thể phân biệt\n" +
                "những hiệu ứng mà ta cảm thấy với trải nghiệm khi có trọng lực.\n" +
                "Ông giả thiết rằng lực hấp dẫn là biểu hiện vênh méo của không\n" +
                "gian và thời gian, và ông đưa ra các phương trình mô tả bằng cách\n" +
                "nào động học của độ cong ấy lại là kết quả của sự tác động qua lại\n" +
                "giữa vật chất, chuyển động và năng lượng. Ta cũng có thể mô tả\n" +
                "nó bằng một thí nghiệm tưởng tượng khác. Hãy hình dung sẽ thế\n" +
                "nào nếu ta lăn một quả bóng bowling lên mặt phẳng hai chiều\n" +
                "của một tấm bạt lò xo. Rồi sau đó lăn tiếp vài quả bi-a. Những quả\n" +
                "bi-a này di chuyển về phía quả bóng bowling không phải là vì quả\n" +
                "bóng bowling tác động một lực hút bí ẩn nào đó, mà là vì nó đã\n" +
                "làm trũng tấm vải bạt. Giờ hãy tưởng tượng điều này diễn ra\n" +
                "trong cấu trúc bốn chiều của không – thời gian. Đúng là chẳng dễ\n" +
                "gì mà tưởng tượng được, nhưng chính vì thế mà chỉ có ông là\n" +
                "Einstein, còn chúng ta thì không.\n" +
                "Một thập kỷ sau đó, năm 1925, là cột mốc đánh dấu nửa sự\n" +
                "nghiệp của Einstein và cũng là một bước ngoặt của ông. Cuộc\n" +
                "cách mạng lượng tử mà ông góp phần tạo nên đang biến thành\n" +
                "một môn cơ học mới dựa trên xác suất và tính bất định. Ông có\n" +
                "những đóng góp vĩ đại cuối cùng cho cơ học lượng tử trong năm\n" +
                "đó, nhưng đồng thời ông cũng bắt đầu phản đối nó. Ông dành ba\n" +
                "thập kỷ sau đó, cuối cùng viết nguệch ngoạc một số phương trình\n" +
                "khi nằm trên giường bệnh vào năm 1955, kiên quyết phê phán\n" +
                "\n" +
                "những gì ông cho là tính không đầy đủ của cơ học lượng tử, đồng\n" +
                "thời cố gắng gộp nó vào một lý thuyết trường thống nhất.\n" +
                "Suốt 30 năm là một “nhà cách mạng” cũng như 30 năm tiếp theo\n" +
                "là người chống đối, trước sau như một, Einstein vẫn tự nguyện là\n" +
                "một người cô độc vui tươi thanh thản, thấy dễ chịu khi không\n" +
                "phải theo lề thói. Là người độc lập trong tư duy, Einstein được dẫn\n" +
                "dắt bởi một trí tưởng tượng vốn thoát khỏi các giới hạn của hiểu\n" +
                "biết thông thường. Ông là một kiểu người kỳ cục, một kẻ nổi loạn\n" +
                "đáng kính, và ông được hướng dẫn bởi một đức tin, mà ông chấp\n" +
                "nhận một cách nhẹ nhàng với đôi mắt sáng lấp lánh, vào một\n" +
                "Thượng đế không chơi trò xúc xắc bằng việc cho phép mọi thứ\n" +
                "diễn ra một cách ngẫu nhiên.\n" +
                "Thiên hướng không theo lề lối của Einstein cũng thể hiện rõ nét\n" +
                "trong nhân cách và quan điểm chính trị của ông. Mặc dù ông tán\n" +
                "thành các lý tưởng xã hội chủ nghĩa, song bản thân ông lại là\n" +
                "người theo chủ nghĩa cá nhân đến độ khó lòng cảm thấy thoải\n" +
                "mái với sự kiểm soát quá mức của nhà nước hay hình thức tập\n" +
                "trung quyền lực. Bản năng bất phục tùng của ông, rất đắc dụng\n" +
                "khi ông là một nhà khoa học trẻ, khiến ông dị ứng với chủ nghĩa\n" +
                "dân tộc, chủ nghĩa quân phiệt và bất cứ điều gì thể hiện tâm tính\n" +
                "đám đông. Cho đến khi Hitler khiến ông phải sửa lại hoàn cảnh\n" +
                "địa chính trị của mình, bản chất ông vẫn là người yêu chuộng hòa\n" +
                "bình, phản đối chiến tranh.\n" +
                "Câu chuyện của ông bao trùm miền rộng lớn của nền khoa học\n" +
                "hiện đại, từ cái vô cùng bé đến cái vô hạn, từ hiện tượng phát ra\n" +
                "photon tới sự giãn nở của vũ trụ. Một thế kỷ sau những thành tựu\n" +
                "vĩ đại của ông, chúng ta vẫn sống trong vũ trụ của Einstein, một\n" +
                "vũ trụ được xác định trên thang đo vĩ mô bằng Thuyết Tương đối\n" +
                "và trên thang đo vi mô bằng cơ học lượng tử, mặc dù vẫn làm ta\n" +
                "hoang mang, song đã chứng tỏ được sức sống lâu bền.\n" +
                "Có thể thấy dấu ấn của ông trong khắp các công nghệ ngày nay.\n" +
                "Pin quang điện và la-de, năng lượng hạt nhân và sợi quang, du\n" +
                "hành vũ trụ, và thậm chí các chất bán dẫn cũng đều được giải\n" +
                "thích từ các lý thuyết của ông. Ông đã ký tên vào bức thư gửi\n" +
                "Tổng thống Franklin Roosevelt nhằm cảnh báo khả năng chế tạo\n" +
                "\n" +
                "ra bom nguyên tử, và những bức thư về phương trình nổi tiếng\n" +
                "của ông liên hệ năng lượng với khối lượng vẫn hiện ra trong tâm\n" +
                "trí chúng ta khi chúng ta mường tượng đám mây hình nấm.\n" +
                "Einstein bắt đầu nổi tiếng khi các phép đo được thực hiện trong\n" +
                "lần nhật thực năm 1919 chứng thực dự đoán của ông về việc lực\n" +
                "hấp dẫn bẻ cong ánh sáng thế nào, trùng hợp và góp phần dẫn\n" +
                "đến sự ra đời của thời kỳ mới – “làm người nổi tiếng”. Ông trở\n" +
                "thành một siêu sao mới của giới làm khoa học, một biểu tượng\n" +
                "nhân văn, một trong những gương mặt nổi tiếng nhất hành tinh.\n" +
                "Công chúng suy nghĩ một cách nghiêm túc để cố gắng hiểu các lý\n" +
                "thuyết của ông, xếp ông vào hàng thiên tài và phong cho ông là\n" +
                "một vị thánh ở ngoài đời.\n" +
                "Nếu ông không có mái tóc xù gây khích động mạnh mẽ, trông\n" +
                "chẳng khác gì một vòng hào quang, và đôi mắt tinh anh, liệu ông\n" +
                "có trở thành hiện thân nổi bật của khoa học hay không? Giả sử,\n" +
                "như trong một thí nghiệm tưởng tượng, rằng ông trông giống\n" +
                "một Max Planck hoặc một Niels Bohr nào đó. Liệu ông có còn ở\n" +
                "trong quỹ đạo nổi tiếng của họ, của một thiên tài khoa học thuần\n" +
                "túy không? Hay ông vẫn bước vào ngôi đền thờ các danh nhân,\n" +
                "nơi có Aristotle, Galileo, và Newton?\n" +
                "Tôi tin sẽ là trường hợp thứ hai. Công trình của ông mang dấu ấn\n" +
                "cá nhân cao, một dấu hiệu giúp người ta có thể dễ dàng nhận ra\n" +
                "nó là của ông, như một tác phẩm của Picasso được dễ dàng nhận\n" +
                "ra là của Picasso. Ông thể hiện nhiều tưởng tượng và nhận thức\n" +
                "được những nguyên lý vĩ đại thông qua các thí nghiệm tưởng\n" +
                "tượng, thay vì bằng những phép quy nạp có phương pháp dựa\n" +
                "trên dữ liệu thực nghiệm. Các lý thuyết được sinh ra từ đó đôi khi\n" +
                "gây sững sờ, bí ẩn và phản trực quan, nhưng chúng chứa đựng\n" +
                "những ý niệm có thể thu hút trí tưởng tượng của giới bình dân:\n" +
                "tính tương đối của không gian và thời gian, E=mc2, sự bẻ cong tia\n" +
                "sáng và sự bẻ cong không gian.\n" +
                "Lòng nhân ái giản đơn ở ông cũng góp thêm vào hào quang của\n" +
                "ông. Tính vững chắc trong con người ông được điều hòa phần nào\n" +
                "bởi tính khiêm nhường xuất phát từ sự nể sợ tự nhiên. Ông có thể\n" +
                "thờ ơ và xa cách với những người gần gũi ông, nhưng đối với\n" +
                "\n" +
                "nhân loại nói chung, ông luôn nhân từ một cách chân thành với\n" +
                "lòng trắc ẩn dịu dàng.\n" +
                "Thế nhưng bên cạnh sức hấp dẫn công chúng và vẻ ngoài dễ gần,\n" +
                "Einstein cũng trở thành biểu tượng cho quan điểm rằng vật lý\n" +
                "hiện đại là thứ gì đó mà người bình thường không thể hiểu được,\n" +
                "là “địa hạt của các chuyên gia sống như các thầy tu”, theo lời của\n" +
                "Giáo sư Dudley Herschbach ở Đại học Harvard. Điều này không\n" +
                "phải lúc nào cũng đúng. Galileo và Newton là hai thiên tài vĩ đại,\n" +
                "nhưng cách giải thích thế giới theo hướng nguyên nhân – hệ quả\n" +
                "bằng cơ học của họ thì hầu hết những người có khả năng tư duy\n" +
                "đều hiểu được. Trong thế kỷ XVIII của Benjamin Franklin11 và\n" +
                "thế kỷ XIX của Thomas Edison12, một người có học thức có thể\n" +
                "thấy quen thuộc với khoa học ở mức nào đó, thậm chí có thể thử\n" +
                "làm khoa học như một kẻ nghiệp dư.\n" +
                "Với những nhu cầu mà thế kỷ XXI đặt ra, cảm thức đại chúng cho\n" +
                "các nỗ lực khoa học, nếu có thể, rất nên được khôi phục. Điều này\n" +
                "không có nghĩa là tất cả những ai theo chuyên ngành văn học đều\n" +
                "phải học một khóa vật lý giản lược, hay luật sư khối doanh nghiệp\n" +
                "nào cũng phải biết cơ học lượng tử. Đúng hơn, nó có nghĩa là việc\n" +
                "nhận thức và trân trọng các phương pháp khoa học là một tài sản\n" +
                "hữu ích đối với toàn thể những công dân có trách nhiệm. Điều mà\n" +
                "khoa học cho chúng ta thấy, một cách rất có ý nghĩa, là mối tương\n" +
                "quan giữa chứng cứ thực tế và những lý thuyết tổng quát, như\n" +
                "được minh họa rất rõ qua cuộc đời của Einstein.\n" +
                "Ngoài ra, biết quý trọng những vinh quang khoa học là đức tính\n" +
                "đáng quý dẫn đến một xã hội tốt đẹp. Nó giúp chúng ta vẫn giữ\n" +
                "được khả năng ngạc nhiên như trẻ thơ đối với những điều bình\n" +
                "thường như quả táo rơi hay thang máy, vốn là đặc trưng ở\n" +
                "Einstein và các nhà vật lý lý thuyết vĩ đại khác.\n" +
                "Chính vì thế mà tìm hiểu Einstein là việc đáng làm. Khoa học thật\n" +
                "cao quý và lý thú, và cuộc theo đuổi khoa học là một sứ mệnh đầy\n" +
                "sức hút, chẳng khác nào một cuộc phiêu lưu của những anh\n" +
                "hùng. Trong những năm tháng cuối đời của Einstein, có lần Sở\n" +
                "Giáo dục Tiểu bang New York đã hỏi ông rằng các trường học nên\n" +
                "chú trọng điều gì. Ông trả lời: “Khi dạy sử, nên bàn luận kỹ về\n" +
                "\n" +
                "những nhân vật mang lại lợi ích cho nhân loại qua sự độc lập của\n" +
                "tính cách và phán đoán.” Einstein thích hợp với phạm trù đó.\n" +
                "Một lúc nào đó khi toán học và khoa học nhận được sự chú trọng\n" +
                "mới khi đối mặt với cạnh tranh toàn cầu, chúng ta cũng nên chú ý\n" +
                "tới phần còn lại trong câu trả lời của Einstein. Ông nói: “Những\n" +
                "phản biện của học sinh nên được đón nhận trên tinh thần thân\n" +
                "thiện. Việc tích lũy kiến thức từ sách vở không được khiến tính\n" +
                "độc lập của học sinh bị mất đi.” Lợi thế cạnh tranh của một xã hội\n" +
                "không đến từ việc nhà trường dạy phép tính nhân và bảng tuần\n" +
                "hoàn tốt thế nào, mà từ việc họ kích thích trí tưởng tượng và tính\n" +
                "sáng tạo giỏi ra sao.\n" +
                "Tôi cho rằng, chìa khóa cho tài năng xuất chúng của Einsten và\n" +
                "những bài học của cuộc đời ông nằm ở đây. Khi còn đi học, ông\n" +
                "chưa bao giờ giỏi học thuộc lòng. Sau này, khi là một nhà lý\n" +
                "thuyết, thành công của ông không đến từ năng lực xử lý của trí\n" +
                "óc, mà đến từ trí tưởng tượng và tính sáng tạo. Ông có thể xây\n" +
                "dựng những phương trình phức tạp, nhưng quan trọng hơn, ông\n" +
                "biết rằng toán học là ngôn ngữ mà tự nhiên dùng để mô tả những\n" +
                "kỳ quan của mình. Vì vậy, ông có thể hình dung các phương trình\n" +
                "thể hiện như thế nào trong thực tế – chẳng hạn, các phương trình\n" +
                "trường điện từ do James Clerk Maxwell tìm ra sẽ thể hiện ra như\n" +
                "thế nào trước một cậu bé đang lướt theo tia sáng. Ông từng tuyên\n" +
                "bố: “Trí tưởng tượng quan trọng hơn kiến thức”.\n" +
                "Phương pháp đó đòi hỏi ở ông sự bất tuân các quy tắc và luật lệ.\n" +
                "Ông đã hân hoan nói với người yêu, người sau này trở thành vợ\n" +
                "ông: “Xấc xược muôn năm. Đó là thiên thần hộ mệnh của anh\n" +
                "trên đời này.” Nhiều năm sau này, khi những người khác nghĩ\n" +
                "rằng việc ông miễn cưỡng chấp nhận cơ học lượng tử chứng tỏ\n" +
                "ông đã không còn như trước, ông than thở: “Để trừng phạt tôi vì\n" +
                "dám xem thường quyền uy, định mệnh đã bắt tôi trở thành kẻ có\n" +
                "quyền uy.”\n" +
                "Thành công của ông đến từ việc hoài nghi những hiểu biết thông\n" +
                "thường, thách thức quyền uy và biết kinh ngạc trước những điều\n" +
                "bí ẩn mà người khác cho là tầm thường. Điều này giúp ông theo\n" +
                "đuổi một nguyên tắc đạo đức và chính trị dựa trên sự tôn trọng\n" +
                "\n" +
                "dành cho những trí tuệ tự do, tinh thần tự do và cá nhân tự do.\n" +
                "Ông cự tuyệt chế độ bạo quyền, và ông thấy rằng sự khoan\n" +
                "nhượng không chỉ là một đức tính hiền lành mà còn là điều kiện\n" +
                "cần thiết cho một xã hội sáng tạo. Ông nói: “Nuôi dưỡng tính cá\n" +
                "nhân rất quan trọng vì chỉ có cá nhân mới có thể đưa ra những ý\n" +
                "tưởng mới mẻ.”\n" +
                "Cách nhìn này khiến Einstein trở thành một kẻ nổi loạn biết tôn\n" +
                "kính sự hài hòa của tự nhiên, một người có sự hòa hợp đúng đắn\n" +
                "trí tưởng tượng và trí tuệ để thay đổi hiểu biết của chúng ta về vũ\n" +
                "trụ. Đây là những tính cách có ý nghĩa sống còn trong thế kỷ toàn\n" +
                "cầu hóa ngày nay, khi mà thành công sẽ phụ thuộc vào tính sáng\n" +
                "tạo, cũng như những năm đầu của thế kỷ XX khi Einstein góp\n" +
                "phần mở ra kỷ nguyên hiện đại.', 'truyenkhoahoc1');");

        // Nguồn Gốc Các Loài
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (2, 'Nguồn Gốc Các Loài', 120000, 'Tìm hiểu về nguồn gốc và tiến hóa của các loài trên trái đất.', 'Truy cập vào quá khứ đầy thú vị của hành tinh chúng ta, cuốn sách chiến tranh với những khám phá tiến bộ về nguồn gốc của các loài. Từ những sự kiện địa chất đến cuộc phiêu lưu tiến hóa, độc giả sẽ hiểu rõ về sự phong phú của sự sống và cách các loài đã phát triển qua hàng triệu năm.', 'truyenkhoahoc2');");

        // Sao Hỏa, Sao Kim
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (2, 'Sao Hỏa, Sao Kim', 180000, 'Một chuyến phiêu lưu không gian qua các hành tinh lớn trong hệ mặt trời.', ' Cuốn sách khám phá không gian không chỉ là về khoa học mà còn về sự hiểu biết sâu sắc về những hành tinh mà chúng ta có thể thăm dò trong tương lai. Với hình ảnh sống động và mô tả sinh động, độc giả sẽ cảm nhận được sự kỳ diệu của vũ trụ và tầm quan trọng của việc khám phá.', 'truyenkhoahoc3');");

        // Franskanstein
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (2, 'Franskanstein', 200000, 'Một tác phẩm kỳ bí hòa quyện giữa ngôn ngữ và khoa học. Cuốn sách này kể về một thế giới nơi ngôn từ và sức mạnh của nó tạo ra những sinh linh mới.', '\"Franskanstein\" là một hành trình qua sự kết hợp giữa khoa học và nghệ thuật, khi một nhà khoa học tài năng tìm kiếm sức mạnh của từ ngôn và tạo ra một sinh linh mới. Tác phẩm không chỉ là một câu chuyện về sự sáng tạo, mà còn là một triết lý về quyền lực và trách nhiệm của con người đối với tạo vật mà mình đã sáng tạo.', 'truyenkhoahoc4');");

        // Bí Ẩn Cơ Thể Con Người
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (2, 'Bí Ẩn Cơ Thể Con Người', 130000, 'Mở cửa vào cơ thể con người, cuốn sách này rải rác những bí ẩn về cấu trúc và hoạt động của chúng ta. Từ não bộ đến các hệ cơ quan, đây là một hành trình thú vị qua người.', '\"Bí Ẩn Cơ Thể Con Người\" là một cuộc phiêu lưu hấp dẫn thông qua các bí mật của cơ thể con người. Từ cấu trúc tới chức năng của mỗi bộ phận, độc giả sẽ hiểu rõ về sự phức tạp và đẹp đẽ của cơ thể, cũng như cách chúng ta duy trì sự sống hàng ngày.', 'truyenkhoahoc5');");

        // 1200
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (3, '1200', 25000, 'Một câu chuyện đầy bí ẩn và sâu sắc về con số 1200. Cuốn sách khám phá ý nghĩa ẩn sau con số này và mở ra một thế giới mới, nơi mà số liệu không chỉ là con số mà còn là chìa khóa của những bí mật tâm linh.', 'là một cuộc phiêu lưu tâm linh, tìm kiếm ý nghĩa của số 1200 trong cuộc sống và vũ trụ. Cuốn sách khám phá ý nghĩa ẩn sau con số này, mở ra một thế giới mới nơi số liệu không chỉ là con số mà còn là chìa khóa của những bí mật tâm linh. Độc giả sẽ được đắm chìm trong những ý tưởng mới mẻ và tận hưởng sự huyền bí của số liệu.', 'truyenngan1');");

        // Viết Lên Hy Vọng
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (3, 'Viết Lên Hy Vọng', 25000, 'Một câu chuyện ngắn đầy tích cực về sức mạnh của hy vọng và quyết tâm trong cuộc sống. Cuốn sách này là hướng dẫn cho những người đang đối mặt với khó khăn và muốn tìm kiếm động lực để vượt qua.', 'là một hành trình tràn ngập năng lượng tích cực, nơi người đọc sẽ được dẫn dắt qua cuộc sống của một người trẻ đầy nhiệt huyết. Đối mặt với những khó khăn trong sự nghiệp và tình yêu, nhân vật chính tôn vinh khả năng đề kháng và lòng tin vào tương lai. Cuốn sách này không chỉ là một câu chuyện mà còn là nguồn động viên mạnh mẽ cho những ai đang đối mặt với thách thức và khao khát tìm kiếm động lực để vượt qua.', 'truyenngan2');");

        // Đợi Cậu Ở Nơi Này
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (3, 'Đợi Cậu Ở Nơi Này', 30000, ' Một câu chuyện lãng mạn về sự kiên trì và tình yêu, nơi một người đợi chờ người khác tại một nơi đặc biệt. Cuốn sách này đưa độc giả vào một hành trình đầy cảm xúc và ý nghĩa về sự chờ đợi.', ' \"Đợi Cậu Ở Nơi Này\" là một tác phẩm lãng mạn về sự kiên trì và tình yêu, nơi người đọc sẽ bước chân vào một hành trình đầy cảm xúc và ý nghĩa về sự chờ đợi. Câu chuyện về tình yêu chân thành và lòng trung thành này sẽ làm cho độc giả trải qua những khắc khoải và niềm vui của người đợi chờ, hiểu rõ hơn về ý nghĩa sâu sắc của tình yêu vững bền.', 'truyenngan3');");

        // Tím Ngát Tuổi 20
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (3, 'Tím Ngát Tuổi 20', 40000, 'Một câu chuyện về tuổi trẻ, những ước mơ và khát vọng. \"Tím Ngát Tuổi 20\" là sự kết hợp tinh tế giữa hài hước và xúc động, khám phá những trăn trở và thách thức của đời trẻ.', 'kết hợp tinh tế giữa hài hước và xúc động, khám phá những trăn trở và thách thức của đời trẻ. Từ việc lựa chọn nghề nghiệp đến những tình bạn và tình yêu đầu đời, cuốn sách này là một hành trình đáng nhớ qua những năm tuổi 20 đầy hứng khởi, nơi những ước mơ và khát vọng được thể hiện một cách sống động.', 'truyenngan4');");

        // Truyện Ngắn Chọn Lọc
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (3, 'Truyện Ngắn Chọn Lọc', 35000, 'Một tuyển tập truyện ngắn nam cao đa dạng về chủ đề và cảm xúc. Cuốn sách mang đến cho độc giả những câu chuyện ngắn, sắc bén và đầy ảo tưởng.', 'một tuyển tập đa dạng về chủ đề và cảm xúc, mang đến cho độc giả những câu chuyện ngắn, sắc bén và đầy ảo tưởng. Từ hài hước đến đau lòng, cuốn sách này là một hành trình qua nhiều cảm xúc và tâm trạng, tạo nên một đại dương ngôn từ phong phú.', 'truyenngan5');");

        // 5 Ngôn Ngữ Yêu Thương
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (1, '5 Ngôn Ngữ Yêu Thương', 35000, 'Khám phá bí mật của tình yêu qua 5 ngôn ngữ giao tiếp: lời nói, thời gian chất lượng, quà tặng ý nghĩa, sự phục vụ và chạm.', 'Cuốn sách chia sẻ cách hiểu và thể hiện tình cảm hiệu quả thông qua việc áp dụng những ngôn ngữ này trong mối quan hệ.', 'anhtinhcam1');");

        // Ảo Tưởng Tình Cảm
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (1, 'Ảo Tưởng Tình Cảm', 45000, 'Khám phá thế giới tình cảm qua khía cạnh ảo tưởng, nơi mà mọi giới hạn đều có thể bị vượt qua bởi sức mạnh của trí tưởng tượng.', 'Tác phẩm mở đầu cánh cửa vào thế giới của trí tưởng tượng và cảm xúc, làm nổi bật tình cảm và kỳ diệu của mối quan hệ qua những khía cạnh ảo tưởng.', 'anhtinhcam2');");

        // Rèn Luyện Tình Cảm
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (1, 'Rèn Luyện Tình Cảm', 40000, 'Hướng dẫn chi tiết về cách rèn luyện và phát triển mối quan hệ tình cảm, từ việc hiểu biết đến xây dựng sự tin tưởng.', 'Cuốn sách này không chỉ tập trung vào việc giúp độc giả hiểu rõ hơn về chính họ mà còn cung cấp những chiến lược cụ thể để rèn luyện và củng cố tình cảm.', 'anhtinhcam3');");

        // Mắt Biết
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (1, 'Mắt Biết', 50000, 'Khoảnh khắc của mắt, sự biểu cảm và ngôn ngữ cơ thể trong mối quan hệ, giúp độc giả hiểu rõ hơn về sự giao tiếp phi lời.', 'Từ cách nhìn vào đôi mắt đến ngôn ngữ cơ thể, cuốn sách giúp độc giả đọc được những dấu hiệu tinh tế trong mối quan hệ và cải thiện giao tiếp.', 'anhtinhcam4');");

        // Tôi Đã Bắt Tình Yêu Lộ Mặt
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (1, 'Tôi Đã Bắt Tình Yêu Lộ Mặt', 55000, 'Một cuộc phiêu lưu đầy hấp dẫn và lãng mạn, kể về hành trình của nhân vật chính trong việc khám phá và bắt lấy tình yêu.', 'Tác phẩm này kết hợp giữa yếu tố lãng mạn và sự hồi hộp của một cuộc phiêu lưu, khi nhân vật chính dấn thân vào hành trình đầy thách thức để tìm thấy và giữ lấy tình yêu lộ mặt.', 'anhtinhcam5');");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
