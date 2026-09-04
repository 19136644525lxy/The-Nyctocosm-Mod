#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s daisID = TEMP daisID
function crafting:dais/effects/__generated__/while/2
execute at @s run function crafting:dais/effects/__generated__/block/30
execute at @s positioned ^ ^ ^1.5 run particle lava ~ ~ ~ 0 0 0 0.1 10
scoreboard players reset NumItems value
tag @s remove justsummoned