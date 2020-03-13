package co.uk.hhservers.frespect.commands;

import co.uk.hhservers.frespect.config.Config;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.World;

public class FCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String player = src.getName();
        Player realPlayer = Sponge.getServer().getPlayer(player).get();
        World playerWorld = realPlayer.getWorld();
        Player oofedPlayer = args.<Player>getOne(Text.of("player")).get();
        String beforeMessage = Config.confNode.getNode("message").getString().replaceAll("RESPECTER", realPlayer.getName()).replaceAll("RESPECTEE", oofedPlayer.getName());
        String message = "&a"+realPlayer.getName()+Config.confNode.getNode("message").getString()+"&a"+oofedPlayer.getName();
        EventContext context = EventContext.builder()
                .add(EventContextKeys.PLAYER_SIMULATED, realPlayer.getProfile())
                .build();
        Cause cause = Cause.builder()
                .append(realPlayer)
                .build(context);
        realPlayer.simulateChat(TextSerializers.FORMATTING_CODE.deserialize(beforeMessage), cause);
        //MessageChannel.world(playerWorld).send(TextSerializers.FORMATTING_CODE.deserialize(message));
        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission("frespect.respect")
                .arguments(GenericArguments.player(Text.of("player")))
                .executor(new FCommand())
                .build();
    }
}
