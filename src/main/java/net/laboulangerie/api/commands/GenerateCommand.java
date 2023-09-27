package net.laboulangerie.api.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.laboulangerie.api.LaBoulangerieAPI;
import net.laboulangerie.api.jwt.JwtLevel;
import net.laboulangerie.api.jwt.JwtUser;

public class GenerateCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias,
            @NotNull String[] args) {

        if (args.length != 2) {
            sender.sendMessage("Invalid usage!");
            return false;
        }

        String name = args[0];
        JwtLevel level;

        try {
            level = JwtLevel.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage("Invalid level!");
            return false;
        }

        JwtUser jwtUser = new JwtUser(name, level);

        Component generatingComp = Component
                .text(String.format("Generating API token for %s on level %s", name, level.name()));
        sender.sendMessage(generatingComp.color(NamedTextColor.GOLD));

        String jwtToken = LaBoulangerieAPI.JWT_MANAGER.getGenerator().generate(jwtUser,
                LaBoulangerieAPI.JWT_MANAGER.getAlgorithm());
        sender.sendMessage(jwtToken);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd,
            @NotNull String alias, @NotNull String[] args) {
        if (args.length == 2) {
            return Stream.of(JwtLevel.values()).map(JwtLevel::name).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

}
