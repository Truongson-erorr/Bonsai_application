package com.example.bonsai_application.model

import com.google.firebase.firestore.FirebaseFirestore

data class User(
    val uid: String = "",
    val email: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val role: String = "user"
)


//fun insertSampleCategories() {
//    val db = FirebaseFirestore.getInstance()
//    val categories = listOf(
//        mapOf("id" to "cat1", "name" to "Cây để bàn", "imageUrl" to "https://example.com/caydeban.jpg"),
//        mapOf("id" to "cat2", "name" to "Cây phong thủy", "imageUrl" to "https://example.com/cayphongthuy.jpg"),
//        mapOf("id" to "cat3", "name" to "Sen đá", "imageUrl" to "https://example.com/senda.jpg"),
//        mapOf("id" to "cat4", "name" to "Xương rồng", "imageUrl" to "https://example.com/xuongrong.jpg"),
//        mapOf("id" to "cat5", "name" to "Cây leo", "imageUrl" to "https://example.com/cayleo.jpg"),
//        mapOf("id" to "cat6", "name" to "Cây văn phòng", "imageUrl" to "https://example.com/cayvanphong.jpg"),
//        mapOf("id" to "cat7", "name" to "Cây thủy sinh", "imageUrl" to "https://example.com/caythuysinh.jpg"),
//        mapOf("id" to "cat8", "name" to "Cây bonsai", "imageUrl" to "https://example.com/caybonsai.jpg"),
//        mapOf("id" to "cat9", "name" to "Cây lọc không khí", "imageUrl" to "https://example.com/loc-khong-khi.jpg"),
//        mapOf("id" to "cat10", "name" to "Cây ăn quả mini", "imageUrl" to "https://example.com/mini-fruit.jpg")
//    )
//
//    categories.forEach { category ->
//        db.collection("Categories").document(category["id"].toString()).set(category)
//    }
//}
//
//fun insertSampleProducts() {
//    val db = FirebaseFirestore.getInstance()
//    val products = listOf(
//        mapOf(
//            "id" to "p1",
//            "name" to "Cây Lưỡi Hổ",
//            "imageUrl" to "https://example.com/luoiho.jpg",
//            "description" to "Thanh lọc không khí, hợp phong thủy",
//            "categoryId" to "cat2",
//            "size" to "Trung bình",
//            "quantityInStock" to 12,
//            "price" to 150000.0,
//            "discountPercent" to 5.0
//        ),
//        mapOf(
//            "id" to "p2",
//            "name" to "Sen đá ngọc",
//            "imageUrl" to "https://example.com/sendangoc.jpg",
//            "description" to "Nhỏ gọn, dễ chăm",
//            "categoryId" to "cat3",
//            "size" to "Nhỏ",
//            "quantityInStock" to 20,
//            "price" to 90000.0,
//            "discountPercent" to 0.0
//        ),
//        mapOf(
//            "id" to "p3",
//            "name" to "Xương rồng nở hoa",
//            "imageUrl" to "https://example.com/xuongronghoa.jpg",
//            "description" to "Dễ sống, không cần nhiều nước",
//            "categoryId" to "cat4",
//            "size" to "Nhỏ",
//            "quantityInStock" to 30,
//            "price" to 80000.0,
//            "discountPercent" to 10.0
//        ),
//        mapOf(
//            "id" to "p4",
//            "name" to "Cây phát tài",
//            "imageUrl" to "https://example.com/phattai.jpg",
//            "description" to "Mang lại may mắn và tài lộc",
//            "categoryId" to "cat2",
//            "size" to "Lớn",
//            "quantityInStock" to 8,
//            "price" to 300000.0,
//            "discountPercent" to 15.0
//        ),
//        mapOf(
//            "id" to "p5",
//            "name" to "Cây trầu bà leo",
//            "imageUrl" to "https://example.com/traubaleo.jpg",
//            "description" to "Lá xanh mát, trang trí đẹp",
//            "categoryId" to "cat5",
//            "size" to "Trung bình",
//            "quantityInStock" to 14,
//            "price" to 120000.0,
//            "discountPercent" to 0.0
//        ),
//        mapOf(
//            "id" to "p6",
//            "name" to "Cây ngọc bích",
//            "imageUrl" to "https://example.com/ngocbich.jpg",
//            "description" to "Cây cảnh phong thủy",
//            "categoryId" to "cat1",
//            "size" to "Nhỏ",
//            "quantityInStock" to 25,
//            "price" to 100000.0,
//            "discountPercent" to 7.0
//        ),
//        mapOf(
//            "id" to "p7",
//            "name" to "Cây bonsai Tùng",
//            "imageUrl" to "https://example.com/bonsaitung.jpg",
//            "description" to "Bonsai nghệ thuật cao",
//            "categoryId" to "cat8",
//            "size" to "Lớn",
//            "quantityInStock" to 5,
//            "price" to 500000.0,
//            "discountPercent" to 20.0
//        ),
//        mapOf(
//            "id" to "p8",
//            "name" to "Cây dứa mini",
//            "imageUrl" to "https://example.com/duamini.jpg",
//            "description" to "Cây ăn quả mini xinh xắn",
//            "categoryId" to "cat10",
//            "size" to "Nhỏ",
//            "quantityInStock" to 10,
//            "price" to 95000.0,
//            "discountPercent" to 0.0
//        ),
//        mapOf(
//            "id" to "p9",
//            "name" to "Cây Bạch Mã Hoàng Tử",
//            "imageUrl" to "https://example.com/bachma.jpg",
//            "description" to "Tăng tài lộc và may mắn",
//            "categoryId" to "cat2",
//            "size" to "Trung bình",
//            "quantityInStock" to 10,
//            "price" to 180000.0,
//            "discountPercent" to 5.0
//        ),
//        mapOf(
//            "id" to "p10",
//            "name" to "Cây Cau Tiểu Trâm",
//            "imageUrl" to "https://example.com/cautieutram.jpg",
//            "description" to "Cây nhỏ để bàn thanh lọc không khí",
//            "categoryId" to "cat1",
//            "size" to "Nhỏ",
//            "quantityInStock" to 18,
//            "price" to 95000.0,
//            "discountPercent" to 0.0
//        ),
//        mapOf(
//            "id" to "p11",
//            "name" to "Cây Hạnh Phúc",
//            "imageUrl" to "https://example.com/hanhphuc.jpg",
//            "description" to "Mang lại cảm giác yên bình cho không gian",
//            "categoryId" to "cat5",
//            "size" to "Lớn",
//            "quantityInStock" to 6,
//            "price" to 350000.0,
//            "discountPercent" to 8.0
//        ),
//        mapOf(
//            "id" to "p12",
//            "name" to "Cây Kim Tiền",
//            "imageUrl" to "https://example.com/kimtien.jpg",
//            "description" to "Thu hút tiền tài và thịnh vượng",
//            "categoryId" to "cat2",
//            "size" to "Trung bình",
//            "quantityInStock" to 12,
//            "price" to 200000.0,
//            "discountPercent" to 10.0
//        ),
//        mapOf(
//            "id" to "p13",
//            "name" to "Cây Vạn Lộc",
//            "imageUrl" to "https://example.com/vanloc.jpg",
//            "description" to "Màu sắc tươi tắn, hút ánh nhìn",
//            "categoryId" to "cat5",
//            "size" to "Nhỏ",
//            "quantityInStock" to 20,
//            "price" to 110000.0,
//            "discountPercent" to 0.0
//        ),
//        mapOf(
//            "id" to "p14",
//            "name" to "Cây Lan Ý",
//            "imageUrl" to "https://example.com/lany.jpg",
//            "description" to "Lọc khí, thích hợp phòng ngủ",
//            "categoryId" to "cat6",
//            "size" to "Nhỏ",
//            "quantityInStock" to 15,
//            "price" to 130000.0,
//            "discountPercent" to 5.0
//        ),
//        mapOf(
//            "id" to "p15",
//            "name" to "Sen đá viền đỏ",
//            "imageUrl" to "https://example.com/sendavienddo.jpg",
//            "description" to "Viền đỏ nổi bật, dễ chăm sóc",
//            "categoryId" to "cat3",
//            "size" to "Nhỏ",
//            "quantityInStock" to 25,
//            "price" to 85000.0,
//            "discountPercent" to 0.0
//        ),
//        mapOf(
//            "id" to "p16",
//            "name" to "Cây Trúc Nhật",
//            "imageUrl" to "https://example.com/trucnhat.jpg",
//            "description" to "Tạo không gian xanh mát, thanh lịch",
//            "categoryId" to "cat4",
//            "size" to "Trung bình",
//            "quantityInStock" to 8,
//            "price" to 140000.0,
//            "discountPercent" to 0.0
//        ),
//        mapOf(
//            "id" to "p17",
//            "name" to "Xương Rồng Trứng Chim",
//            "imageUrl" to "https://example.com/xrtrungchim.jpg",
//            "description" to "Dáng nhỏ gọn, độc đáo",
//            "categoryId" to "cat4",
//            "size" to "Nhỏ",
//            "quantityInStock" to 16,
//            "price" to 75000.0,
//            "discountPercent" to 0.0
//        ),
//        mapOf(
//            "id" to "p18",
//            "name" to "Cây Cọ Nhật Mini",
//            "imageUrl" to "https://example.com/conhat.jpg",
//            "description" to "Trang trí nội thất, sang trọng",
//            "categoryId" to "cat1",
//            "size" to "Nhỏ",
//            "quantityInStock" to 10,
//            "price" to 125000.0,
//            "discountPercent" to 0.0
//        ),
//        mapOf(
//            "id" to "p19",
//            "name" to "Sen đá nâu",
//            "imageUrl" to "https://example.com/sendanau.jpg",
//            "description" to "Lá nâu đặc trưng, đẹp mắt",
//            "categoryId" to "cat3",
//            "size" to "Nhỏ",
//            "quantityInStock" to 22,
//            "price" to 87000.0,
//            "discountPercent" to 0.0
//        ),
//        mapOf(
//            "id" to "p20",
//            "name" to "Cây May Mắn",
//            "imageUrl" to "https://example.com/mayman.jpg",
//            "description" to "Thường dùng làm quà tặng",
//            "categoryId" to "cat9",
//            "size" to "Nhỏ",
//            "quantityInStock" to 30,
//            "price" to 75000.0,
//            "discountPercent" to 5.0
//        ),
//        mapOf(
//            "id" to "p21",
//            "name" to "Cây Sung Mỹ Mini",
//            "imageUrl" to "https://example.com/sungmy.jpg",
//            "description" to "Cây ăn quả mini đẹp, lạ",
//            "categoryId" to "cat10",
//            "size" to "Nhỏ",
//            "quantityInStock" to 10,
//            "price" to 170000.0,
//            "discountPercent" to 10.0
//        ),
//        mapOf(
//            "id" to "p22",
//            "name" to "Cây Bonsa Cam Quýt",
//            "imageUrl" to "https://example.com/bonsaicamquyt.jpg",
//            "description" to "Cây cảnh mini có quả",
//            "categoryId" to "cat8",
//            "size" to "Lớn",
//            "quantityInStock" to 6,
//            "price" to 450000.0,
//            "discountPercent" to 15.0
//        ),
//        mapOf(
//            "id" to "p23",
//            "name" to "Cây Dây Nhện",
//            "imageUrl" to "https://example.com/daynhen.jpg",
//            "description" to "Lọc khí, treo tường đẹp",
//            "categoryId" to "cat5",
//            "size" to "Trung bình",
//            "quantityInStock" to 14,
//            "price" to 98000.0,
//            "discountPercent" to 0.0
//        )
//    )
//
//    products.forEach { product ->
//        db.collection("Products").document(product["id"].toString()).set(product)
//    }
//}