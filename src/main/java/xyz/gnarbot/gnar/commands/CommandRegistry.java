package xyz.gnarbot.gnar.commands;

import xyz.gnarbot.gnar.Bot;
import xyz.gnarbot.gnar.commands.executors.admin.*;
import xyz.gnarbot.gnar.commands.executors.fun.*;
import xyz.gnarbot.gnar.commands.executors.games.GameLookupCommand;
import xyz.gnarbot.gnar.commands.executors.games.LeagueLookupCommand;
import xyz.gnarbot.gnar.commands.executors.games.OverwatchLookupCommand;
import xyz.gnarbot.gnar.commands.executors.general.*;
import xyz.gnarbot.gnar.commands.executors.media.*;
import xyz.gnarbot.gnar.commands.executors.mod.DisableCommand;
import xyz.gnarbot.gnar.commands.executors.mod.EnableCommand;
import xyz.gnarbot.gnar.commands.executors.mod.PruneCommand;
import xyz.gnarbot.gnar.commands.executors.music.*;
import xyz.gnarbot.gnar.commands.executors.music.dj.*;
import xyz.gnarbot.gnar.commands.executors.polls.PollCommand;
import xyz.gnarbot.gnar.commands.executors.test.TestCommand;
import xyz.gnarbot.gnar.commands.executors.test.TestEmbedCommand;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * A registry storing CommandExecutor entries for the bot.
 */
public class CommandRegistry {
    private final Bot bot;

    /** The mapped registry of invoking key to the classes. */
    private final Map<String, CommandExecutor> commandEntryMap = new LinkedHashMap<>();

    public CommandRegistry(Bot bot) {
        this.bot = bot;

        register(new HelpCommand());
        register(new InviteBotCommand());
        register(new PingCommand());
        register(new MathCommand());
        register(new RemindMeCommand());
        register(new GoogleCommand());
        register(new YoutubeCommand());
        register(new UrbanDictionaryCommand());
        register(new UptimeCommand());
        register(new WhoIsCommand());
        register(new BotInfoCommand());
        register(new DonateCommand());
        //End General Commands

        //Fun Commands
        register(new ASCIICommand());
        register(new CoinFlipCommand());
        register(new DialogCommand());
        register(new YodaTalkCommand());
        register(new RollCommand());
        register(new PoopCommand());
        register(new GoodShitCommand());
        register(new EightBallCommand());
        register(new LeetifyCommand());
        //register(new ProgressionCommand());
        register(new GoogleyEyesCommand());
        //register(new ServersSharedCommand());
        register(new TextToSpeechCommand());
        register(new ReactCommand());
        register(new ChampDataCommand());
        register(new TriviaAnswerCommand());
        register(new TriviaCommand());
        //register(new GraphCommand());

        register(new ChampQuoteCommand());
        register(new PandoraBotCommand());
        register(new MemeCommand());
        //End Fun Commands

        //Mod Commands
        register(new PruneCommand());
        //End Mod Commands

        //Testing Commands
        register(new TestCommand());
        //End Testing Commands

        //Game Commands
        register(new OverwatchLookupCommand());
        register(new LeagueLookupCommand());
        register(new GameLookupCommand());
        //End Game Commands

        //Poll Commands
        register(new PollCommand());
        //End Poll Commands

        //Media Commands
        register(new CatsCommand());
        register(new ExplosmCommand());
        register(new ExplosmRCGCommand());
        register(new XKCDCommand());
        //End Media Commands

        // Administrator commands
        register(new GarbageCollectCommand());
        register(new RestartShardsCommand());
        register(new JavascriptCommand());
        register(new GroovyCommand());
        register(new ShardInfoCommand());
        register(new ThrowError());
        register(new UpdateCommand());
        register(new RestartBotCommand());
        register(new EmoteListCommand());


        // Test Commands
        register(new TestEmbedCommand());
        register(new QuoteCommand());
        register(new TextToBrickCommand());

        //MUSIC COMMAND
        if (bot.getConfig().getMusicEnabled()) {
            register(new PlayCommand());
            //register(new LeaveCommand()); // Useless
            register(new PauseCommand());
            register(new StopCommand());
            register(new SkipCommand());
            register(new ShuffleCommand());
            register(new NowPlayingCommand());
            register(new QueueCommand());
            register(new RestartCommand());
            register(new RepeatCommand());
            register(new ResetCommand());
            register(new VoteSkipCommand());
            register(new ChooseCommand());
        }

        register(new EnableCommand());
        register(new DisableCommand());
        //register(new ListDisabledCommand());
    }

    public Map<String, CommandExecutor> getCommandMap() {
        return commandEntryMap;
    }

    public void register(CommandExecutor cmd) {
        Class<? extends CommandExecutor> cls = cmd.getClass();
        if (!cls.isAnnotationPresent(Command.class)) {
            throw new IllegalStateException("@Command annotation not found for class: " + cls.getName());
        }

        for (String alias : cmd.getInfo().aliases()) {
            registerCommand(alias, cmd);
        }
    }

    /**
     * Register the CommandExecutor instance into the registry.
     * @param label Invoking key.
     * @param cmd Command entry.
     */
    public void registerCommand(String label, CommandExecutor cmd) {
        label = label.toLowerCase();
        if (commandEntryMap.containsKey(label)) {
            throw new IllegalStateException("Command " + label + " is already registered.");
        }
        commandEntryMap.put(label, cmd);
    }

    /**
     * Unregisters a CommandExecutor.
     *
     * @param label Invoking key.
     */
    public void unregisterCommand(String label) {
        commandEntryMap.remove(label);
    }

    /**
     * Returns the command registry.
     *
     * @return The command registry.
     */
    public Set<CommandExecutor> getEntries() {
        return new LinkedHashSet<>(commandEntryMap.values());
    }

    public CommandExecutor getCommand(String label) {
        return commandEntryMap.get(label);
    }
}
