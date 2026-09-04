#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score SoulCount value run data get entity @s Inventory[{Slot:-106b}].tag.souls_held
scoreboard players add SoulCount value 1
execute store result storage spells:soul SoulCount int 1 run scoreboard players get SoulCount value
item modify entity @s weapon.offhand spells:planering/count_from_storage
item modify entity @s weapon.offhand spells:planering/update_lore