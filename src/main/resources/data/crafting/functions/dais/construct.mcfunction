#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set Tester daisID 0
execute as @e[tag=dais] if score @s daisID = Tester daisID run tag @s add idOccupied
function crafting:dais/__generated__/while/0
tag @e remove idOccupied
summon marker ~ ~ ~ {Tags:["dais","root","justsummoned"]}
execute as @e[tag=dais,tag=justsummoned] at @s run function crafting:dais/__generated__/block/19
scoreboard players reset Tester daisID