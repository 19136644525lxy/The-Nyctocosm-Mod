#built using mc-build (https://github.com/mc-build/mc-build)

execute at @e[tag=segment,tag=serpent,limit=10,sort=random] run function effects:soul_swirl/randomsingle
scoreboard players reset SerpentDeath timer
execute at @e[tag=serpent,tag=segment] run particle flash ~ ~ ~ 0 0 0 0 1 force
execute at @e[tag=serpent,tag=head] run function serpent:__generated__/block/3
execute as @a[tag=s.damagedSerpent] run function serpent:__generated__/block/5
function serpent:remove