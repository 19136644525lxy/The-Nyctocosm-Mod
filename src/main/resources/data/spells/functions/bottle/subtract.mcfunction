#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score SoulCount value run data get entity @s SelectedItem.tag.souls_held
scoreboard players remove SoulCount value 1
execute store result storage spells:soul SoulCount int 1 run scoreboard players get SoulCount value
item modify entity @s weapon.mainhand spells:soulbottle/count_from_storage
item modify entity @s weapon.mainhand spells:soulbottle/update_lore
execute if score SoulCount value matches ..0 run item replace entity @s weapon.mainhand with minecraft:glass_bottle