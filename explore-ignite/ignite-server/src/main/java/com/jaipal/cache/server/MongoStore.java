package com.jaipal.cache.server;

import com.jaipal.model.Player;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lang.IgniteBiInClosure;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.io.Serializable;
import java.util.List;


/**
 * Created by dreddy on 11/10/2016.
 */
public class MongoStore extends CacheStoreAdapter<String, Player> implements Serializable {

    public static MongoPersistence persistence = new MongoPersistence();

    @Override
    public Player load(String key) {
        return persistence.get(key);
    }

    @Override
    public void write(Cache.Entry<? extends String, ? extends Player> entry) throws CacheWriterException {
        persistence.insert(entry.getValue());
    }

    @Override
    public void delete(Object key) {
        persistence.delete((Integer) key);
    }

    @Override
    public void loadCache(IgniteBiInClosure<String, Player> clo, Object... args) {
        if (args == null || args.length == 0 || args[0] == null)
            throw new CacheLoaderException("Expected entry count parameter is not provided.");

        final int entryCnt = (Integer) args[0];
        List<Player> rs = persistence.getAll();
        int cnt = 0;
        while (cnt < entryCnt && rs.size() > cnt) {
            clo.apply(rs.get(cnt).getUsername(), rs.get(cnt));
            cnt++;
        }
        System.out.println("************************ Records "+clo.toString());
    }


}

