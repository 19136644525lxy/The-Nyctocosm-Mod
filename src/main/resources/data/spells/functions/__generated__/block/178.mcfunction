#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score TempSoulCount value run data get entity @s Inventory[{Slot:-106b}].tag.souls_held 1
execute if score TempSoulCount value matches 1.. run function spells:__generated__/block/179
execute as @s[predicate=spells:ring/wearing_active_ring,tag=!toggle] run function spells:__generated__/block/181
tag @s remove toggle