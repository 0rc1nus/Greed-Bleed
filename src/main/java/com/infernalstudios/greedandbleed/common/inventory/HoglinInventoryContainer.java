package com.infernalstudios.greedandbleed.common.inventory;

import com.infernalstudios.greedandbleed.common.entity.IHasMountArmor;
import com.infernalstudios.greedandbleed.common.entity.IHasMountInventory;
import com.infernalstudios.greedandbleed.common.entity.IToleratingMount;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HoglinInventoryContainer extends Container {
   private final IInventory hoglinContainer;
   private final AnimalEntity hoglin;

   public HoglinInventoryContainer(int containerId, PlayerInventory playerInventory, IInventory inventory, final AnimalEntity hoglin) {
      super((ContainerType<?>)null, containerId);
      this.hoglinContainer = inventory;
      if(!(hoglin instanceof IEquipable)
              || !(hoglin instanceof IHasMountArmor)
              || !(hoglin instanceof IToleratingMount)){
         throw new IllegalArgumentException("This entity type " + hoglin.getType() + " is not valid for HoglinInventoryContainer!");
      }
      this.hoglin = hoglin;
      int i = 3;
      inventory.startOpen(playerInventory.player);
      int j = -18;
      this.addSlot(new Slot(inventory, 0, 8, 18) {
         public boolean mayPlace(ItemStack stack) {
            return ((IToleratingMount) hoglin).isSaddleStack(stack)
                    && !this.hasItem()
                    && ((IEquipable) hoglin).isSaddleable();
         }

         @OnlyIn(Dist.CLIENT)
         public boolean isActive() {
            return ((IEquipable)hoglin).isSaddleable();
         }
      });
      this.addSlot(new Slot(inventory, 1, 8, 36) {
         public boolean mayPlace(ItemStack stack) {
            return ((IHasMountArmor)hoglin).isArmor(stack);
         }

         @OnlyIn(Dist.CLIENT)
         public boolean isActive() {
            return ((IHasMountArmor)hoglin).canWearArmor();
         }

         public int getMaxStackSize() {
            return 1;
         }
      });
      if (hoglin instanceof IHasMountInventory && ((IHasMountInventory)hoglin).hasChest()) {
         for(int k = 0; k < 3; ++k) {
            for(int l = 0; l < ((IHasMountInventory)hoglin).getInventoryColumns(); ++l) {
               this.addSlot(new Slot(inventory, 2 + l + k * ((IHasMountInventory)hoglin).getInventoryColumns(), 80 + l * 18, 18 + k * 18));
            }
         }
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int k1 = 0; k1 < 9; ++k1) {
            this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
      }

   }

   public boolean stillValid(PlayerEntity player) {
      return this.hoglinContainer.stillValid(player) && this.hoglin.isAlive() && this.hoglin.distanceTo(player) < 8.0F;
   }

   public ItemStack quickMoveStack(PlayerEntity player, int slotIndex) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.slots.get(slotIndex);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         int i = this.hoglinContainer.getContainerSize();
         if (slotIndex < i) {
            if (!this.moveItemStackTo(itemstack1, i, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(1).mayPlace(itemstack1) && !this.getSlot(1).hasItem()) {
            if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(0).mayPlace(itemstack1)) {
            if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (i <= 2 || !this.moveItemStackTo(itemstack1, 2, i, false)) {
            int j = i + 27;
            int k = j + 9;
            if (slotIndex >= j && slotIndex < k) {
               if (!this.moveItemStackTo(itemstack1, i, j, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (slotIndex >= i && slotIndex < j) {
               if (!this.moveItemStackTo(itemstack1, j, k, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.moveItemStackTo(itemstack1, j, j, false)) {
               return ItemStack.EMPTY;
            }

            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }
      }

      return itemstack;
   }

   public void removed(PlayerEntity player) {
      super.removed(player);
      this.hoglinContainer.stopOpen(player);
   }
}