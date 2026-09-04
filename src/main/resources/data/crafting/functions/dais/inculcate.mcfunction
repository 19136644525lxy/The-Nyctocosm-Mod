#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation Tester daisID = @s daisID
execute as @e[tag=dais] if score @s daisID = Tester daisID run tag @s add matchedid
execute at @s positioned ~ ~1.625 ~ run function inculcate:summon
execute as @e[tag=dais,tag=aj.inculcate.root,tag=justsummoned] at @s run function crafting:dais/__generated__/block/21
tag @e[tag=matchedid,tag=dais,tag=ring] add converge
scoreboard players operation @s craftindex = CraftIndex value
tag @e remove matchedid
scoreboard players reset Tester daisID