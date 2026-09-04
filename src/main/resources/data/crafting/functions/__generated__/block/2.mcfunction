#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation Tester daisID = @s daisID
execute as @e[tag=dais] if score @s daisID = Tester daisID run tag @s add matchedid
scoreboard players remove @s daishumtimer 1
execute if score @s daishumtimer matches ..0 run function crafting:__generated__/block/3
execute unless block ~ ~-1 ~ minecraft:anvil run function crafting:__generated__/block/4
tag @e remove matchedid