package com.chosencraft.www.modreqrevised.database.sql;

import com.chosencraft.www.modreqrevised.database.sql.query.ExecuteQuery;
import com.chosencraft.www.modreqrevised.database.sql.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Consumer implements Runnable
{

    private Database db;

    private static Consumer instance;

    public static Consumer getInstance()
    {
        return Consumer.instance;
    }

    public static synchronized void queue(Query query)
    {
        Consumer.getInstance().queries.add(query);
    }

    private BukkitTask task;

    private Queue<Query> queries;

    public Consumer(Plugin pl, Database db)
    {
        this.db = db;
        Consumer.instance = this;

        queries = new LinkedBlockingQueue<Query>();

        task = Bukkit.getScheduler().runTaskTimerAsynchronously(pl, this, 1L, 5L);
    }

    @Override
    public void run()
    {
        if (!queries.isEmpty())
        {
            if (db.isConnected())
            {
                Query query;
                while ((query = queries.poll()) != null)
                {
                    try
                    {
                        if (query instanceof ExecuteQuery)
                        {
                            ExecuteQuery executeQuery = (ExecuteQuery) query;

                            Statement statement = db.createStatement();
                            ResultSet set = statement.executeQuery(query.getQuery());

                            executeQuery.execute(set);

                            set.close();
                            statement.close();

                        } else
                        {
                            Statement statement = db.createStatement();
                            statement.execute(query.getQuery());
                        }
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void stop()
    {
        task.cancel();
    }

}
