package com.splendor.fruit.commands;

import com.splendor.fruit.FruitMain;
import com.splendor.fruit.guis.SaplingList;
import com.splendor.fruit.manager.SpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    private final SpManager manager = FruitMain.getSpmanager();
    private final SaplingList gui = new SaplingList();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

        if (!(sender instanceof Player))
            return false;

        Player p = (Player) sender;

        if (!p.isOp())
            return false;

        if (args.length == 0) {
            p.sendMessage("/채집 추가 [이름]");
            p.sendMessage("/채집 제거 [이름]");
            p.sendMessage("/채집 설정 [이름]");
            p.sendMessage("/채집 목록");
            p.sendMessage("/채집 리로드");
            return true;
        }

        if (args[0].equals("목록")) {
            p.openInventory(gui.getInv());
            return true;
        }

        if (args[0].equals("리로드")) {
            FruitMain.getInstance().reloadData();
            return true;
        }

        if (args.length != 2) {
            p.sendMessage("/채집 추가 [이름]");
            p.sendMessage("/채집 제거 [이름]");
            p.sendMessage("/채집 설정 [이름]");
            return true;
        }

        if (args[0].equals("추가")) {
            if (manager.isSapling(args[1])) {
                p.sendMessage("이미 생성되어있는 이름입니다");
                return false;
            }
            if (p.getInventory().getItemInMainHand() == null) {
                p.sendMessage("손에 묘목으로 설정할 아이템을 들어주세요");
                return false;
            }
            p.sendMessage("§f\"" + args[1] + "\" 묘목을 §a생성 §f하였습니다");
            p.sendMessage("/채집 설정 으로 머리와 지연 시간을 설정해주세요");
            manager.registerSapling(args[1], p.getInventory().getItemInMainHand());
            return true;
        }

        if (args[0].equals("제거")) {
            if (!manager.isSapling(args[1])) {
                p.sendMessage("없는 이름입니다");
                return false;
            }
            p.sendMessage("§f\"" + args[1] + "\" 묘목을 §c제거 §f하였습니다");
            manager.unregisterSapling(args[1]);
            return true;
        }

        if (args[0].equals("설정")) {
            if (!manager.isSapling(args[1])) {
                p.sendMessage("없는 이름입니다");
                return false;
            }
        }
        return false;
    }
}
