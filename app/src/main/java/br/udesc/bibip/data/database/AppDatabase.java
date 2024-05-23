package br.udesc.bibip.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.udesc.bibip.data.dao.AbastecimentoDao;
import br.udesc.bibip.data.dao.AnotacaoDao;
import br.udesc.bibip.data.dao.UsuarioDao;
import br.udesc.bibip.data.dao.VeiculoDao;
import br.udesc.bibip.data.model.AbastecimentoModel;
import br.udesc.bibip.data.model.AnotacaoModel;
import br.udesc.bibip.data.model.UsuarioModel;
import br.udesc.bibip.data.model.VeiculoModel;
import br.udesc.bibip.data.utils.ConverterUtil;

@Database(entities = {UsuarioModel.class, VeiculoModel.class, AbastecimentoModel.class, AnotacaoModel.class}, version = 1, exportSchema = false)
@TypeConverters({ConverterUtil.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();
    public abstract VeiculoDao veiculoDao();
    public abstract AbastecimentoDao abastecimentoDao();
    public abstract AnotacaoDao anotacaoDao();
}
