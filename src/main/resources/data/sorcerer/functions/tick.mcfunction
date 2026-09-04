#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=sorcererattack] at @s run function sorcerer:__generated__/block/0
function sorcerer:ai/subtick
execute as @e[tag=sorcerer] at @s run function sorcerer:ai/entitytick