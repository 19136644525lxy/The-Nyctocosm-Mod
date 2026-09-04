#built using mc-build (https://github.com/mc-build/mc-build)

particle flash ~ ~ ~ 0.1 0.1 0.1 0 5 force
particle lava ~ ~ ~ 0 0 0 0.1 10
particle explosion ~ ~ ~ 0.1 0.1 0.1 0 10
function effects:shockwave/inculcate
scoreboard players operation Tester daisID = @s daisID
execute as @e[tag=dais] if score @s daisID = Tester daisID run tag @s add matchedid
execute as @e[tag=matchedid,tag=dais,tag=ring] at @s run tag @s remove converge
execute as @e[tag=dais,tag=root,tag=matchedid] at @s run function crafting:dais/effects/__generated__/block/26
tag @e remove matchedid
scoreboard players reset Tester daisID
function inculcate:remove/this