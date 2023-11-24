package model.impl;

import db.DBConnection;
import dto.ItemDto;
import model.ItemModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModelImpl implements ItemModel {


    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO item VALUES (?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, dto.getCode());
        pstm.setString(2, dto.getDesc());
        pstm.setDouble(3, dto.getUnitPrice());
        pstm.setInt(4, dto.getQty());
        return pstm.executeUpdate() > 0;

    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE item SET description=?, unitPrice=?,qtyOnHand =? WHERE code=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, dto.getDesc());
        pstm.setDouble(2, dto.getUnitPrice());
        pstm.setInt(3, dto.getQty());
        pstm.setString(4, dto.getCode());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {

        String sql = "DELETE from item WHERE code=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    @Override
    public List<ItemDto> allItem() throws SQLException, ClassNotFoundException {
        List<ItemDto> list = new ArrayList<>();

        String sql = "SELECT * FROM item";
        return getItemDtos(list, sql);
    }


    @Override
    public List<ItemDto> searchItem(String id) throws SQLException, ClassNotFoundException {
        List<ItemDto> list = new ArrayList<>();

        String sql = "SELECT * FROM item WHERE code LIKE '" + id + "%'";
        return getItemDtos(list, sql);
    }

    private List<ItemDto> getItemDtos(List<ItemDto> list, String sql) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            list.add(new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }
        return list;
    }

}
