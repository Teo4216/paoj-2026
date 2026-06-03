package com.pao.laboratory14.exercise2.repository;

import com.pao.laboratory14.exercise1.TipBilet;
import com.pao.laboratory14.exercise2.model.Eveniment;
import com.pao.laboratory14.exercise2.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EvenimentRepository implements Repository<Eveniment, Integer> {

    private Connection getConn() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public void initSchema() throws SQLException {
        try (Statement stmt = getConn().createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS evenimente");
            stmt.execute("CREATE TABLE evenimente (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nume TEXT NOT NULL, " +
                    "data TEXT NOT NULL, " +
                    "capacitate INTEGER, " +
                    "tip TEXT)");
        }
    }

    @Override
    public void save(Eveniment eveniment) throws SQLException {
        String sql = "INSERT INTO evenimente (nume, data, capacitate, tip) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, eveniment.getNume());
            ps.setString(2, eveniment.getData());
            ps.setInt(3, eveniment.getCapacitate());
            ps.setString(4, eveniment.getTip().name());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    eveniment.setId(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public List<Eveniment> findAll() throws SQLException {
        String sql = "SELECT id, nume, data, capacitate, tip FROM evenimente ORDER BY id";
        List<Eveniment> lista = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Eveniment ev = new Eveniment();
                ev.setId(rs.getInt("id"));
                ev.setNume(rs.getString("nume"));
                ev.setData(rs.getString("data"));
                ev.setCapacitate(rs.getInt("capacitate"));
                ev.setTip(TipBilet.valueOf(rs.getString("tip")));
                lista.add(ev);
            }
        }
        return lista;
    }

    public int deleteImpl(int id) throws SQLException {
        String sql = "DELETE FROM evenimente WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM evenimente";
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public Optional<Eveniment> findById(Integer id) throws SQLException {
        String sql = "SELECT id, nume, data, capacitate, tip FROM evenimente WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Eveniment ev = new Eveniment();
                    ev.setId(rs.getInt("id"));
                    ev.setNume(rs.getString("nume"));
                    ev.setData(rs.getString("data"));
                    ev.setCapacitate(rs.getInt("capacitate"));
                    ev.setTip(TipBilet.valueOf(rs.getString("tip")));
                    return Optional.of(ev);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Eveniment eveniment) throws SQLException {
        String sql = "UPDATE evenimente SET nume = ?, data = ?, capacitate = ?, tip = ? WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, eveniment.getNume());
            ps.setString(2, eveniment.getData());
            ps.setInt(3, eveniment.getCapacitate());
            ps.setString(4, eveniment.getTip().name());
            ps.setInt(5, eveniment.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        deleteImpl(id);
    }
}