#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation RotX e.value += @s e.angveloc
execute if score RotX e.value matches 900.. run function effects:soul_swirl/__generated__/block/636