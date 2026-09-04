#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation Tester daisID = @s daisID
execute as @e[tag=dais] if score @s daisID = Tester daisID run tag @s add matchedid
data merge entity @s {Health:1000000}
execute store result score KillTrader value run data get entity @s Offers.Recipes.[0].uses
execute if score KillTrader value matches 1 run function crafting:__generated__/block/6
execute store result score KillTrader value run data get entity @s Offers.Recipes.[1].uses
execute if score KillTrader value matches 1 run function crafting:__generated__/block/7
execute store result score KillTrader value run data get entity @s Offers.Recipes.[2].uses
execute if score KillTrader value matches 1 run function crafting:__generated__/block/8
execute store result score KillTrader value run data get entity @s Offers.Recipes.[3].uses
execute if score KillTrader value matches 1 run function crafting:__generated__/block/9
scoreboard players reset KillTrader value
execute as @s[tag=dais,tag=matchedid,tag=reset] unless entity @e[tag=aj.inculcate.root,tag=matchedid] run function crafting:__generated__/block/10
kill @s[tag=kill]
tag @e remove matchedid