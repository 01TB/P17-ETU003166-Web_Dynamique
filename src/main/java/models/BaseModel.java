package models;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connection.MySQLConnection;

public class BaseModel {

    public Integer id;

    public BaseModel() {
    }

    public BaseModel(Integer id) {
        setId(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected String getTableName() {
        return this.getClass().getSimpleName().toLowerCase();
    }


    public BaseModel save() throws Exception {
        try (Connection connection = MySQLConnection.getConnection()) {
            Field[] fields = this.getAllFields();

            if (getId() == null) {
                StringBuilder columns = new StringBuilder();
                StringBuilder values = new StringBuilder();
                List<Object> paramValues = new ArrayList<>();

                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();

                    if (!fieldName.equals("id")) {
                        Object value = field.get(this);

                        if (columns.length() > 0)
                            columns.append(", ");
                        columns.append(fieldName.toLowerCase());

                        if (values.length() > 0)
                            values.append(", ");
                        values.append("?");

                        paramValues.add(value);
                    }
                }

                String sql = "INSERT INTO " + getTableName() + " (" + columns + ") VALUES (" + values + ")";
                try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    setParameters(stmt, paramValues);
                    stmt.executeUpdate();

                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            setId(generatedKeys.getInt(1));
                        }
                    }
                }
            } else {
                StringBuilder updates = new StringBuilder();
                List<Object> paramValues = new ArrayList<>();

                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();

                    if (!fieldName.equals("id")) {
                        Object value = field.get(this);

                        if (updates.length() > 0)
                            updates.append(", ");
                        updates.append(fieldName.toLowerCase()).append(" = ?");
                        paramValues.add(value);
                    }
                }

                String sql = "UPDATE " + getTableName() + " SET " + updates + " WHERE id = ?";
                paramValues.add(getId());

                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    setParameters(stmt, paramValues);
                    stmt.executeUpdate();
                    stmt.close();
                }
            }
            connection.close();
            return this;
        }
    }

    public void delete() throws Exception {
        if (getId() == null) {
            throw new IllegalStateException("Impossible de supprimer une entité sans identifiant.");
        }

        try (Connection connection = MySQLConnection.getConnection()) {
            String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setObject(1, getId());
                stmt.executeUpdate();
                stmt.close();
            }
            connection.close();
        }
    }

    public static <E extends BaseModel> E findById(Class<E> entityClass, Integer id) throws Exception {
        String tableName = entityClass.getSimpleName().toLowerCase();
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (Connection connection = MySQLConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs, entityClass);
                }
                rs.close();
            }
            stmt.close();
            connection.close();
        }

        return null;
    }

    public static <E extends BaseModel> List<E> findAll(Class<E> entityClass) throws Exception {
        String tableName = entityClass.getSimpleName().toLowerCase();
        String sql = "SELECT * FROM " + tableName;
        List<E> entities = new ArrayList<>();

        try (Connection connection = MySQLConnection.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs, entityClass));
            }
            rs.close();
            stmt.close();
            connection.close();
        }

        return entities;
    }

    public Field[] getAllFields() {
        Class<?> clazz = this.getClass();
        List<Field> list = new ArrayList<>();

        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                list.add(field);
            }
            clazz = clazz.getSuperclass();
        }

        return list.toArray(new Field[0]);
    }

    protected static <E extends BaseModel> E mapResultSetToEntity(ResultSet rs, Class<E> entityClass)
            throws Exception {

        E entity = entityClass.getDeclaredConstructor().newInstance();
        Field[] fields = entity.getAllFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName().toLowerCase();

            try {
                Object value = rs.getObject(fieldName);
                if (value != null) {
                    if (field.getType() == Long.class || field.getType() == long.class) {
                        field.set(entity, ((Number) value).longValue());
                    } else if (field.getType() == Integer.class || field.getType() == int.class) {
                        field.set(entity, ((Number) value).intValue());
                    } else if (field.getType() == Double.class || field.getType() == double.class) {
                        field.set(entity, ((Number) value).doubleValue());
                    } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
                        if (value instanceof Boolean) {
                            field.set(entity, value);
                        } else {
                            field.set(entity, Boolean.parseBoolean(value.toString()));
                        }
                    } else if (field.getType() == Date.class ) {
                        field.set(entity, Date.valueOf(value.toString()));
                    }
                    else {
                        field.set(entity, value);
                    }
                }
            } catch (SQLException e) {
                // Ignorer les colonnes non correspondantes
            }
        }

        return entity;
    }

    private void setParameters(PreparedStatement stmt, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }
    }
}
