package com.yifei.nyctocosm.dais;

import com.yifei.nyctocosm.Content;
import com.yifei.nyctocosm.CommonEvents;
import java.util.Comparator;
import java.util.List;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

/**
 * Two-input dais crafting container backed by data-driven {@link DaisRecipe}s.
 *
 * <p>The result is computed only on the server (the client menu receives the synced slot), so
 * dedicated servers do not need recipe knowledge on the client to render the GUI. Recipes are
 * looked up from the vanilla {@code RecipeManager}, which means datapack recipes and
 * {@code /reload} are picked up automatically.</p>
 */
public final class DaisMenu extends AbstractContainerMenu {
    private final SimpleContainer inputs = new SimpleContainer(2);
    private final ResultContainer result = new ResultContainer();
    private final ContainerLevelAccess access;
    private final Level level;
    private boolean crafting;

    public DaisMenu(int id, Inventory inventory) {
        this(id, inventory, ContainerLevelAccess.NULL, null);
    }

    public DaisMenu(int id, Inventory inventory, ContainerLevelAccess access) {
        this(id, inventory, access, null);
    }

    public DaisMenu(int id, Inventory inventory, ContainerLevelAccess access, Level level) {
        super(Content.DAIS_MENU.get(), id);
        this.access = access;
        this.level = level;
        inputs.addListener(container -> slotsChanged(container));
        addSlot(new Slot(inputs, 0, 35, 40));
        addSlot(new Slot(inputs, 1, 71, 40));
        addSlot(new Slot(result, 0, 129, 40) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player player) {
                return !getItem().isEmpty();
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                DaisRecipe recipe = current();
                if (recipe != null) {
                    crafting = true;
                    inputs.removeItem(0, recipe.firstCount());
                    inputs.removeItem(1, recipe.secondCount());
                    crafting = false;
                    slotsChanged(inputs);
                    access.execute((level, pos) -> {
                        if (level instanceof net.minecraft.server.level.ServerLevel server) {
                            var center = net.minecraft.world.phys.Vec3.atCenterOf(pos);
                            // 原包 crafting:dais/inculcate：召唤 111 帧 AJ rig 并播 master 音效；粒子由 rig 关键帧在 10/76/85/99/110 tick 驱动
                            CommonEvents.soundMaster(server, center, "dais.inculcate", 5, 30);
                            server.addFreshEntity(new InculcateRig(server, center.add(0, 1.625, 0)));
                        }
                    });
                }
                super.onTake(player, stack);
            }
        });
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(inventory, col, 8 + col * 18, 142));
        }
    }

    /**
     * Best matching data-driven recipe, or {@code null} on the client / when nothing matches.
     * Higher {@link DaisRecipe#priority()} wins; ties are broken by recipe id for determinism.
     */
    @SuppressWarnings("unchecked")
    private DaisRecipe current() {
        if (level == null || level.isClientSide) {
            return null;
        }
        RecipeType<DaisRecipe> type = (RecipeType<DaisRecipe>) Content.DAIS_RECIPE_TYPE.get();
        List<DaisRecipe> recipes = level.getRecipeManager().getAllRecipesFor(type);
        return recipes.stream()
            .filter(recipe -> recipe.matches(inputs, level))
            .min(Comparator.comparingInt(DaisRecipe::priority).reversed().thenComparing(recipe -> recipe.getId().toString()))
            .orElse(null);
    }

    @Override
    public void slotsChanged(Container container) {
        if (crafting || level == null || level.isClientSide) {
            return;
        }
        DaisRecipe recipe = current();
        result.setItem(0, recipe == null ? ItemStack.EMPTY : recipe.getResultItem(level.registryAccess()).copy());
        broadcastChanges();
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, Content.DAIS.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getItem(), copy = stack.copy();
        if (index == 2) {
            int capacity = 0;
            for (int n = 3; n < 39; n++) {
                Slot destination = slots.get(n);
                ItemStack present = destination.getItem();
                if (present.isEmpty()) {
                    capacity += Math.min(destination.getMaxStackSize(stack), stack.getMaxStackSize());
                } else if (ItemStack.isSameItemSameTags(present, stack)) {
                    capacity += Math.max(0, Math.min(destination.getMaxStackSize(stack), stack.getMaxStackSize()) - present.getCount());
                }
            }
            if (capacity < stack.getCount() || !moveItemStackTo(stack, 3, 39, true)) {
                return ItemStack.EMPTY;
            }
            slot.onQuickCraft(stack, copy);
        } else if (index < 2) {
            if (!moveItemStackTo(stack, 3, 39, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!moveItemStackTo(stack, 0, 2, false)) {
            return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        if (stack.getCount() == copy.getCount()) {
            return ItemStack.EMPTY;
        }
        slot.onTake(player, stack);
        return copy;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        access.execute((level, pos) -> clearContainer(player, inputs));
    }
}
