package com.yifei.nyctocosm.item.book;

import com.yifei.nyctocosm.NyctocosmMod;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * 卷册类物品注册中心（单一职责：注册 5 本 Nyctocosm 书籍）。
 * <p>
 * 原数据包以 written_book + NBT pages/author/title 方式实现，
 * 此处继承 {@link WrittenBookItem} 获得原版右键阅读 GUI 能力。
 * 书页内容通过翻译键 NBT 存储，客户端按语言设置动态渲染。
 *
 * <ul>
 *   <li>journal      —— 《久远日志》</li>
 *   <li>catechism     —— 《夜宙教理问答》</li>
 *   <li>dreams         —— 《远梦残页》</li>
 *   <li>axiotopology   —— 《维度拓扑学》</li>
 *   <li>adherent       —— 《夜宙·信徒修行笔记》</li>
 * </ul>
 */
public final class NyctocosmBookItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NyctocosmMod.MOD_ID);

    public static final RegistryObject<Item> JOURNAL = registerBook("journal", Rarity.UNCOMMON);
    public static final RegistryObject<Item> CATECHISM = registerBook("catechism", Rarity.UNCOMMON);
    public static final RegistryObject<Item> DREAMS = registerBook("dreams", Rarity.UNCOMMON);
    public static final RegistryObject<Item> AXIOTOPOLOGY = registerBook("axiotopology", Rarity.UNCOMMON);
    public static final RegistryObject<Item> ADHERENT = registerBook("adherent", Rarity.UNCOMMON);

    private NyctocosmBookItems() {}

    private static RegistryObject<Item> registerBook(String bookId, Rarity rarity) {
        return ITEMS.register(bookId,
                () -> new NyctocosmBookItem(bookId, new Item.Properties().stacksTo(1).rarity(rarity)));
    }

    public static void init(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    /**
     * 创建带完整 NBT 的书籍 ItemStack（用于创造栏 /give）。
     * NBT 包含 title/author/generation/pages/resolved，
     * pages 存储翻译键 JSON，客户端按语言设置动态渲染。
     */
    public static ItemStack createBookStack(String bookId) {
        Item item = switch (bookId) {
            case "journal" -> JOURNAL.get();
            case "catechism" -> CATECHISM.get();
            case "dreams" -> DREAMS.get();
            case "axiotopology" -> AXIOTOPOLOGY.get();
            case "adherent" -> ADHERENT.get();
            default -> throw new IllegalArgumentException("Unknown book: " + bookId);
        };
        ItemStack stack = new ItemStack(item);
        CompoundTag tag = stack.getOrCreateTag();
        // title/author 用翻译键 JSON 格式，客户端自动解析
        tag.putString("title", "{\"translate\":\"item.nyctocosm." + bookId + "\"}");
        tag.putString("author", "{\"translate\":\"item.nyctocosm." + bookId + ".author\"}");
        tag.putInt("generation", 0);
        tag.putBoolean("resolved", false);
        // pages：每条为翻译键 JSON 文本组件
        ListTag pages = new ListTag();
        pages.add(StringTag.valueOf("{\"translate\":\"item.nyctocosm." + bookId + ".page1\"}"));
        if (bookId.equals("adherent")) {
            pages.add(StringTag.valueOf("{\"translate\":\"item.nyctocosm.adherent.1.page2\"}"));
        }
        tag.put("pages", pages);
        return stack;
    }

    /**
     * 继承 WrittenBookItem 获得原版右键阅读 GUI（player.openItemGui）。
     * 额外重写 appendHoverText 显示 Author + Lore。
     */
    static class NyctocosmBookItem extends WrittenBookItem {

        private final String bookId;

        NyctocosmBookItem(String bookId, Item.Properties props) {
            super(props);
            this.bookId = bookId;
        }

        @Override
        public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level,
                                    @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
            tooltip.add(Component.translatable("item.nyctocosm." + bookId + ".author")
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}
