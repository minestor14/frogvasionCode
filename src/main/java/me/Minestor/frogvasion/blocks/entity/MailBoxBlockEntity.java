package me.Minestor.frogvasion.blocks.entity;

import me.Minestor.frogvasion.blocks.custom.MailBoxBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MailBoxBlockEntity extends BlockEntity {
    private String mail0 = "";
    private String mail1 = "";
    private String mail2 = "";
    private String mail3 = "";
    private String mail4 = "";
    private String mail5 = "";
    private String mail6 = "";
    private String mail7 = "";
    private String mail8 = "";
    private String mail9 = "";
    
    public MailBoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAILBOX_TYPE, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putString("mail0", mail0);
        nbt.putString("mail1", mail1);
        nbt.putString("mail2", mail2);
        nbt.putString("mail3", mail3);
        nbt.putString("mail4", mail4);
        nbt.putString("mail5", mail5);
        nbt.putString("mail6", mail6);
        nbt.putString("mail7", mail7);
        nbt.putString("mail8", mail8);
        nbt.putString("mail9", mail9);

        super.writeNbt(nbt);
        
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        mail0 = nbt.getString("mail0");
        mail1 = nbt.getString("mail1");
        mail2 = nbt.getString("mail2");
        mail3 = nbt.getString("mail3");
        mail4 = nbt.getString("mail4");
        mail5 = nbt.getString("mail5");
        mail6 = nbt.getString("mail6");
        mail7 = nbt.getString("mail7");
        mail8 = nbt.getString("mail8");
        mail9 = nbt.getString("mail9");
    }

    public void addMail(String text) {
        int a = getAmountFull();

        switch (a) {
            case 0 -> mail0 = text;
            case 1 -> mail1 = text;
            case 2 -> mail2 = text;
            case 3 -> mail3 = text;
            case 4 -> mail4 = text;
            case 5 -> mail5 = text;
            case 6 -> mail6 = text;
            case 7 -> mail7 = text;
            case 8 -> mail8 = text;
            case 9 -> mail9 = text;
        }
    }
    @Nullable
    public String getAndRemoveOldestMail() {
        if(Objects.equals(mail0, "")) return null;
        String latest = mail0;
        removeOldest();

        return latest;
    }

    public static void tick(World world, BlockPos pos, BlockState state, MailBoxBlockEntity be) {
        if(!world.isClient) {
            world.setBlockState(pos,state.with(MailBoxBlock.MAIL, be.getAmountFull()));
            world.updateComparators(pos,state.getBlock());
        }
    }

    private void removeOldest() {
        mail0 = mail1;
        mail1 = mail2;
        mail2 = mail3;
        mail3 = mail4;
        mail4 = mail5;
        mail5 = mail6;
        mail6 = mail7;
        mail7 = mail8;
        mail8 = mail9;
        mail9 = "";
    }
    
     public int getAmountFull() {
        return getMessages().size();
     }
     public List<String> getMessages() {
        List<String> l = new ArrayList<>();

         if(!mail0.equalsIgnoreCase("")) l.add(mail0);
         if(!mail1.equalsIgnoreCase("")) l.add(mail1);
         if(!mail2.equalsIgnoreCase("")) l.add(mail2);
         if(!mail3.equalsIgnoreCase("")) l.add(mail3);
         if(!mail4.equalsIgnoreCase("")) l.add(mail4);
         if(!mail5.equalsIgnoreCase("")) l.add(mail5);
         if(!mail6.equalsIgnoreCase("")) l.add(mail6);
         if(!mail7.equalsIgnoreCase("")) l.add(mail7);
         if(!mail8.equalsIgnoreCase("")) l.add(mail8);
         if(!mail9.equalsIgnoreCase("")) l.add(mail9);

         return l;
     }
     public void setBlank(int lastBlank) {
        mail9 = "";
        if(lastBlank < 9) {
            mail8 = "";
        }
        if(lastBlank < 8) {
            mail7 = "";
        }
        if(lastBlank < 7) {
            mail6 = "";
        }
        if(lastBlank < 6) {
            mail5 = "";
        }
        if(lastBlank < 5) {
            mail4 = "";
        }
        if(lastBlank < 4) {
            mail3 = "";
        }
        if(lastBlank < 3) {
            mail2 = "";
        }
        if(lastBlank < 2) {
            mail1 = "";
        }
        if(lastBlank < 1) {
            mail0 = "";
        }
     }
     public String getLowestBlank() {
        int a = getAmountFull() + 1;

        return switch (a) {
            case 1 -> mail0;
            case 2 -> mail1;
            case 3 -> mail2;
            case 4 -> mail3;
            case 5 -> mail4;
            case 6 -> mail5;
            case 7 -> mail6;
            case 8 -> mail7;
            case 9 -> mail8;
            case 10 -> mail9;
            default -> throw new IllegalStateException("Unexpected value: " + a);
        };
     }
}
