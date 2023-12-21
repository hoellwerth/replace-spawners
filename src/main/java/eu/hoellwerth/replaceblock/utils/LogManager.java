package eu.hoellwerth.replaceblock.utils;

import eu.hoellwerth.replaceblock.ReplaceBlock;
import org.bukkit.Bukkit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class LogManager {
    private File file;

    public LogManager() {
        file = new File(ReplaceBlock.INSTANCE.getDataFolder(), "log.yml");
    }

    public void writeToLog(String prefix, String log, LogLevels logLevel) {
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            long currentTimeMillis = System.currentTimeMillis();
            Date currentDate = new Date(currentTimeMillis);

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
            String formattedDateTime = sdf.format(currentDate);
            if(logLevel == LogLevels.TRACE) {
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.trace.console"))
                    Bukkit.getConsoleSender().sendMessage("[" + formattedDateTime + "] [§7TRACE§r] [" + prefix + "] " + log);
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.trace.log")) {
                    writer.append("[" + formattedDateTime + "] [TRACE] [" + prefix + "] " + log);
                    writer.newLine();
                }
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.trace.ingame")) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if(player.hasPermission(Objects.requireNonNull(ReplaceBlock.INSTANCE.getConfig().getString("log.ingamePermission")))) {
                            player.sendMessage("[" + formattedDateTime + "] [§7TRACE§r] [" + prefix + "] " + log);
                        }
                    });
                }
            }
            if(logLevel == LogLevels.DEBUG) {
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.debug.console"))
                    Bukkit.getConsoleSender().sendMessage("[" + formattedDateTime + "] [§aDEBUG§r] [" + prefix + "] " + log);
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.debug.log")) {
                    writer.append("[" + formattedDateTime + "] [DEBUG] [" + prefix + "] " + log);
                    writer.newLine();
                }
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.debug.ingame")) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if(player.hasPermission(Objects.requireNonNull(ReplaceBlock.INSTANCE.getConfig().getString("log.ingamePermission")))) {
                            player.sendMessage("[" + formattedDateTime + "] [§aDEBUG§r] [" + prefix + "] " + log);
                        }
                    });
                }
            }
            if(logLevel == LogLevels.INFO) {
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.info.console"))
                    Bukkit.getConsoleSender().sendMessage("[" + formattedDateTime + "] [§9INFO§r] [" + prefix + "] " + log);
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.info.log")) {
                    writer.append("[" + formattedDateTime + "] [INFO] [" + prefix + "] " + log);
                    writer.newLine();
                }
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.info.ingame")) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if(player.hasPermission(Objects.requireNonNull(ReplaceBlock.INSTANCE.getConfig().getString("log.ingamePermission")))) {
                            player.sendMessage("[" + formattedDateTime + "] [§9INFO§r] [" + prefix + "] " + log);
                        }
                    });
                }
            }
            if(logLevel == LogLevels.WARN) {
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.warn.console"))
                    Bukkit.getConsoleSender().sendMessage("[" + formattedDateTime + "] [§eWARN§r] [" + prefix + "] " + log);
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.warn.log")) {
                    writer.append("[" + formattedDateTime + "] [WARN] [" + prefix + "] " + log);
                    writer.newLine();
                }
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.warn.ingame")) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if(player.hasPermission(Objects.requireNonNull(ReplaceBlock.INSTANCE.getConfig().getString("log.ingamePermission")))) {
                            player.sendMessage("[" + formattedDateTime + "] [§eWARN§r] [" + prefix + "] " + log);
                        }
                    });
                }
            }
            if(logLevel == LogLevels.ERROR) {
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.error.console"))
                    Bukkit.getConsoleSender().sendMessage("[" + formattedDateTime + "] [§cERROR§r] [" + prefix + "] " + log);
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.error.log")) {
                    writer.append("[" + formattedDateTime + "] [ERROR] [" + prefix + "] " + log);
                    writer.newLine();
                }
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.error.ingame")) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if(player.hasPermission(Objects.requireNonNull(ReplaceBlock.INSTANCE.getConfig().getString("log.ingamePermission")))) {
                            player.sendMessage("[" + formattedDateTime + "] [§cERROR§r] [" + prefix + "] " + log);
                        }
                    });
                }
            }
            if(logLevel == LogLevels.CRITICAL) {
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.critical.console"))
                    Bukkit.getConsoleSender().sendMessage("[" + formattedDateTime + "] [§4CRITICAL§r] [" + prefix + "] " + log);
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.critical.log")) {
                    writer.append("[" + formattedDateTime + "] [CRITICAL] [" + prefix + "] " + log);
                    writer.newLine();
                }
                if(ReplaceBlock.INSTANCE.getConfig().getBoolean("log.critical.ingame")) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if(player.hasPermission(Objects.requireNonNull(ReplaceBlock.INSTANCE.getConfig().getString("log.ingamePermission")))) {
                            player.sendMessage("[" + formattedDateTime + "] [§4CRITICAL§r] [" + prefix + "] " + log);
                        }
                    });
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
