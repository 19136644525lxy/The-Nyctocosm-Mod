#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation Tester daisID = @s daisID
execute as @e[tag=dais] if score @s daisID = Tester daisID run tag @s add matchedid
execute as @e[tag=dais,tag=itemindicator,tag=matchedid] at @s run kill @s
scoreboard players reset Tester daisID
tag @e remove matchedid