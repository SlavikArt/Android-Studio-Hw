package com.slavikart.hw_10.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.slavikart.hw_10.model.ShoppingList;
import com.slavikart.hw_10.model.Product;
import com.slavikart.hw_10.model.Type;
import java.util.ArrayList;

public class DatabaseOperations {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseOperations(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertList(ShoppingList list) {
        ContentValues values = new ContentValues();
        values.put("name", list.getName());
        values.put("date", list.getDate());
        values.put("description", list.getDescription());
        return db.insert(DatabaseHelper.TABLE_LISTS, null, values);
    }

    public ArrayList<ShoppingList> getAllLists() {
        ArrayList<ShoppingList> lists = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_LISTS, null, null, null, null, null, "date DESC");
        
        if (cursor.moveToFirst())
            do {
                ShoppingList list = new ShoppingList(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getString(3)
                );
                lists.add(list);
            } while (cursor.moveToNext());
        
        cursor.close();
        return lists;
    }

    public long insertProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("count", product.getCount());
        values.put("list_id", product.getListId());
        values.put("checked", product.getChecked());
        values.put("count_type", product.getCountType());
        long result = db.insert(DatabaseHelper.TABLE_PRODUCTS, null, values);
        android.util.Log.d("DatabaseOperations", "Inserting product: " + product.getName() + ", result: " + result);
        return result;
    }

    public void updateProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("count", product.getCount());
        values.put("count_type", product.getCountType());
        db.update(DatabaseHelper.TABLE_PRODUCTS, values, "_id = ?", new String[]{String.valueOf(product.getId())});
    }

    public ArrayList<Product> getProductsForList(long listId) {
        ArrayList<Product> products = new ArrayList<>();
        String selection = "list_id = ?";
        String[] selectionArgs = {String.valueOf(listId)};
        
        Cursor cursor = db.query(DatabaseHelper.TABLE_PRODUCTS, null, selection, selectionArgs, null, null, null);
        
        if (cursor.moveToFirst())
            do {
                Product product = new Product(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getLong(3),
                    cursor.getInt(4),
                    cursor.getLong(5)
                );
                products.add(product);
            } while (cursor.moveToNext());
        
        cursor.close();
        return products;
    }

    public ArrayList<Type> getAllTypes() {
        ArrayList<Type> types = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TYPES, null, null, null, null, null, null);
        
        if (cursor.moveToFirst())
            do {
                Type type = new Type(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2)
                );
                types.add(type);
            } while (cursor.moveToNext());
        
        cursor.close();
        return types;
    }

    public void updateProductChecked(long productId, int checked) {
        ContentValues values = new ContentValues();
        values.put("checked", checked);
        db.update(DatabaseHelper.TABLE_PRODUCTS, values, "_id = ?", new String[]{String.valueOf(productId)});
    }

    public void deleteList(long listId) {
        db.delete(DatabaseHelper.TABLE_PRODUCTS, "list_id = ?", new String[]{String.valueOf(listId)});
        db.delete(DatabaseHelper.TABLE_LISTS, "_id = ?", new String[]{String.valueOf(listId)});
    }

    public void deleteProduct(long productId) {
        db.delete(DatabaseHelper.TABLE_PRODUCTS, "_id = ?", new String[]{String.valueOf(productId)});
    }
} 