package com.example.foodproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.SharedPreferences;

import com.example.foodproject.R;
import com.example.foodproject.models.CartModel;
import com.example.foodproject.models.FeaturedVerModel;
import com.example.foodproject.models.HomeVerModel;
import com.example.foodproject.models.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FoodDB";
    private static final int DATABASE_VERSION = 1;

    // Bảng Users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_IS_ADMIN = "is_admin";

    // Bảng Products
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_PRODUCT_ID = "id";
    private static final String COLUMN_PRODUCT_NAME = "name";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    private static final String COLUMN_PRODUCT_PRICE = "price";
    private static final String COLUMN_PRODUCT_IMAGE = "image";
    private static final String COLUMN_PRODUCT_CATEGORY = "category";
    private static final String COLUMN_PRODUCT_RATING = "rating";

    // Bảng Orders
    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ORDER_ID = "id";
    private static final String COLUMN_ORDER_USER_ID = "user_id";
    private static final String COLUMN_ORDER_DATE = "order_date";
    private static final String COLUMN_ORDER_STATUS = "status";
    private static final String COLUMN_ORDER_TOTAL = "total";

    // Bảng OrderDetails
    private static final String TABLE_ORDER_DETAILS = "order_details";
    private static final String COLUMN_ORDER_DETAIL_ID = "id";
    private static final String COLUMN_ORDER_DETAIL_ORDER_ID = "order_id";
    private static final String COLUMN_ORDER_DETAIL_PRODUCT_ID = "product_id";
    private static final String COLUMN_ORDER_DETAIL_QUANTITY = "quantity";
    private static final String COLUMN_ORDER_DETAIL_PRICE = "price";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Users
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_IS_ADMIN + " INTEGER DEFAULT 0"
                + ")";

        // Tạo bảng Products
        String createProductsTable = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PRODUCT_NAME + " TEXT,"
                + COLUMN_PRODUCT_DESCRIPTION + " TEXT,"
                + COLUMN_PRODUCT_PRICE + " REAL,"
                + COLUMN_PRODUCT_IMAGE + " INTEGER,"
                + COLUMN_PRODUCT_CATEGORY + " TEXT,"
                + COLUMN_PRODUCT_RATING + " REAL"
                + ")";

        // Tạo bảng Orders
        String createOrdersTable = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ORDER_USER_ID + " INTEGER,"
                + COLUMN_ORDER_DATE + " DATETIME,"
                + COLUMN_ORDER_STATUS + " TEXT,"
                + COLUMN_ORDER_TOTAL + " REAL,"
                + "FOREIGN KEY(" + COLUMN_ORDER_USER_ID + ") REFERENCES "
                + TABLE_USERS + "(" + COLUMN_ID + ")"
                + ")";

        // Tạo bảng OrderDetails
        String createOrderDetailsTable = "CREATE TABLE " + TABLE_ORDER_DETAILS + "("
                + COLUMN_ORDER_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ORDER_DETAIL_ORDER_ID + " INTEGER,"
                + COLUMN_ORDER_DETAIL_PRODUCT_ID + " INTEGER,"
                + COLUMN_ORDER_DETAIL_QUANTITY + " INTEGER,"
                + COLUMN_ORDER_DETAIL_PRICE + " REAL,"
                + "FOREIGN KEY(" + COLUMN_ORDER_DETAIL_ORDER_ID + ") REFERENCES "
                + TABLE_ORDERS + "(" + COLUMN_ORDER_ID + "),"
                + "FOREIGN KEY(" + COLUMN_ORDER_DETAIL_PRODUCT_ID + ") REFERENCES "
                + TABLE_PRODUCTS + "(" + COLUMN_PRODUCT_ID + ")"
                + ")";

        db.execSQL(createUsersTable);
        db.execSQL(createProductsTable);
        db.execSQL(createOrdersTable);
        db.execSQL(createOrderDetailsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean addUser(String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);

        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_USERNAME + "=?", new String[]{username},
                null, null, null);

        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=?", new String[]{email},
                null, null, null);

        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    public long addProduct(String name, String description, double price, 
                            int image, String category, double rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, name);
        values.put(COLUMN_PRODUCT_DESCRIPTION, description);
        values.put(COLUMN_PRODUCT_PRICE, price);
        values.put(COLUMN_PRODUCT_IMAGE, image);
        values.put(COLUMN_PRODUCT_CATEGORY, category);
        values.put(COLUMN_PRODUCT_RATING, rating);
        
        return db.insert(TABLE_PRODUCTS, null, values);
    }

    public long createOrder(int userId, double total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_USER_ID, userId);
        values.put(COLUMN_ORDER_DATE, System.currentTimeMillis());
        values.put(COLUMN_ORDER_STATUS, "Pending");
        values.put(COLUMN_ORDER_TOTAL, total);
        return db.insert(TABLE_ORDERS, null, values);
    }

    public void addOrderDetail(long orderId, long productId, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_DETAIL_ORDER_ID, orderId);
        values.put(COLUMN_ORDER_DETAIL_PRODUCT_ID, productId);
        values.put(COLUMN_ORDER_DETAIL_QUANTITY, quantity);
        values.put(COLUMN_ORDER_DETAIL_PRICE, price);
        db.insert(TABLE_ORDER_DETAILS, null, values);
    }

    public ArrayList<HomeVerModel> getProductsByCategory(String category) {
        ArrayList<HomeVerModel> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String[] columns = {
            COLUMN_PRODUCT_ID,
            COLUMN_PRODUCT_NAME,
            COLUMN_PRODUCT_DESCRIPTION,
            COLUMN_PRODUCT_PRICE,
            COLUMN_PRODUCT_IMAGE,
            COLUMN_PRODUCT_RATING
        };
        
        String selection = COLUMN_PRODUCT_CATEGORY + "=?";
        String[] selectionArgs = {category};
        
        Cursor cursor = db.query(TABLE_PRODUCTS, columns, selection, selectionArgs, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                int imageIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE);
                int nameIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME);
                int descIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION);
                int ratingIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_RATING);
                int priceIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PRICE);
                
                HomeVerModel product = new HomeVerModel(
                    cursor.getInt(imageIndex),
                    cursor.getString(nameIndex),
                    cursor.getString(descIndex),
                    cursor.getString(ratingIndex),
                    cursor.getString(priceIndex)
                );
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    public void addSampleProducts() {

        addProduct("Pizza Hải Sản", "Pizza hải sản tươi ngon", 15.99, R.drawable.pizza1, "Pizza", 4.5);
        addProduct("Pizza Bò", "Pizza bò thơm ngon", 14.99, R.drawable.pizza2, "Pizza", 4.3);
        addProduct("Pizza Nm", "Pizza nấm tươi ngon", 13.99, R.drawable.pizza3, "Pizza", 4.2);
        
        addProduct("Burger Gà", "Burger gà giòn", 8.99, R.drawable.burger1, "Hamburger", 4.4);
        addProduct("Burger Bò", "Burger bò đặc biệt", 9.99, R.drawable.burger2, "Hamburger", 4.6);
        addProduct("Burger Trứng", "Burger trứng giòn", 7.99, R.drawable.burger4, "Hamburger", 4.3);
        
        addProduct("Khoai tây chiên", "Khoai tây chiên giòn", 4.99, R.drawable.fries1, "Fries", 4.2);
        addProduct("Khoai tây nghiền", "Khoai tây nghiền giòn", 4.99, R.drawable.fries2, "Fries", 4.3);
        addProduct("Khoai tây lắc", "Khoai tây lắc giòn", 4.99, R.drawable.fries3, "Fries", 4.4);
        
        addProduct("Kem Vanilla", "Kem vanilla mát lạnh", 3.99, R.drawable.icecream1, "Ice cream", 4.3);
        addProduct("Kem Chocolate", "Kem chocolate mát lạnh", 3.99, R.drawable.icecream2, "Ice cream", 4.4);
        addProduct("Kem Dâu", "Kem dâu mát lạnh", 3.99, R.drawable.icecream3, "Ice cream", 4.5);
        addProduct("Kem sữa", "Kem sữa mát lạnh", 3.99, R.drawable.icecream4, "Ice cream", 4.5);

        addProduct("Sandwich Gà", "Sandwich gà tươi", 6.99, R.drawable.sandwich1, "Sandwich", 4.4);
        addProduct("Sandwich Bò", "Sandwich bò đặc biệt", 7.99, R.drawable.sandwich2, "Sandwich", 4.6);
        addProduct("Sandwich Trứng", "Sandwich trứng giòn", 5.99, R.drawable.sandwich3, "Sandwich", 4.3);
    }

    public int getCurrentUserId() {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("current_user_id", -1);
    }

    public void saveOrder(List<CartModel> cartItems) {
        int userId = getCurrentUserId();
        if (userId == -1) return;

        double total = 0;
        for (CartModel item : cartItems) {
            String priceStr = item.getPrice().replace("$", "")
                                       .replace("Tối thiểu - ", "");
            try {
                double price = Double.parseDouble(priceStr);
                total += price * item.getQuantity();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        long orderId = createOrder(userId, total);

        for (CartModel item : cartItems) {
            String priceStr = item.getPrice().replace("$", "")
                                       .replace("Tối thiểu - ", "");
            try {
                double price = Double.parseDouble(priceStr);
                addOrderDetail(orderId, getProductIdByName(item.getName()), 
                             item.getQuantity(), price);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private long getProductIdByName(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_PRODUCT_ID};
        String selection = COLUMN_PRODUCT_NAME + "=?";
        String[] selectionArgs = {productName};
        
        Cursor cursor = db.query(TABLE_PRODUCTS, columns, selection, selectionArgs,
                null, null, null);
        
        long productId = -1;
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID);
            productId = cursor.getLong(idIndex);
        }
        cursor.close();
        return productId;
    }

    public int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};
        
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);
        
        int userId = -1;
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
            userId = cursor.getInt(idIndex);
        }
        cursor.close();
        return userId;
    }

    public void saveCurrentUserId(int userId) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("current_user_id", userId);
        editor.apply();
    }

   public void resetDatabase() {
       SQLiteDatabase db = this.getWritableDatabase();

       // Xóa tất cả các bảng
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_DETAILS);
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

       // Tạo lại các bảng
       onCreate(db);

       // Thêm lại dữ liệu mẫu
       addSampleProducts();
   }

    public void deleteProduct(long productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_PRODUCT_ID + "=?", 
            new String[]{String.valueOf(productId)});
    }

    public void updateProduct(long productId, String name, String description, 
                             double price, String category, double rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, name);
        values.put(COLUMN_PRODUCT_DESCRIPTION, description);
        values.put(COLUMN_PRODUCT_PRICE, price);
        values.put(COLUMN_PRODUCT_CATEGORY, category);
        values.put(COLUMN_PRODUCT_RATING, rating);
        
        db.update(TABLE_PRODUCTS, values, COLUMN_PRODUCT_ID + "=?",
            new String[]{String.valueOf(productId)});
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME)));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PRICE)));
                product.setImage(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE)));
                product.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_CATEGORY)));
                product.setRating(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_RATING)));
                
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    public boolean isAdmin(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, 
            new String[]{"is_admin"},
            "id = ?",
            new String[]{String.valueOf(userId)},
            null, null, null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(0) == 1;
        }
        return false;
    }
}