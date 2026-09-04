#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add Tester daisID 1
tag @e remove idOccupied
execute as @e[tag=dais] if score @s daisID = Tester daisID run tag @s add idOccupied
scoreboard players set #WHILE_0 LANG_MC_INTERNAL 1
function crafting:dais/__generated__/while/0