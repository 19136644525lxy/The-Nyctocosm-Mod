#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score SoulCount value run data get entity @s SelectedItem.tag.souls_held
scoreboard players add SoulCount value 50
execute store result storage spells:soul SoulCount int 1 run scoreboard players get SoulCount value
item modify entity @s weapon.mainhand spells:soulbottle/count_from_storage
item modify entity @s weapon.mainhand spells:soulbottle/update_lore