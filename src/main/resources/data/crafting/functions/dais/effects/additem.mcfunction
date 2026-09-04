#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation Tester daisID = @s daisID
execute as @e[tag=dais] if score @s daisID = Tester daisID run tag @s add matchedid
scoreboard players operation TEMP daisID = @s daisID
execute at @s run summon marker ~ ~1 ~ {Tags:["dais","itemindicator","justsummoned"]}
execute store result score NumItems value run execute if entity @e[tag=dais,tag=itemindicator,tag=matchedid]
execute as @e[tag=dais,tag=itemindicator,tag=justsummoned] at @s run function crafting:dais/effects/__generated__/block/29
scoreboard players reset TEMP daisID
scoreboard players reset Tester daisID
tag @e remove matchedid