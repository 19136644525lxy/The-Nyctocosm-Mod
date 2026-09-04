#built using mc-build (https://github.com/mc-build/mc-build)

execute at @e[tag=emissary,tag=locator,tag=center] run summon marker ~ ~ ~ {Tags:["emissary_swirl","justsummoned"]}
execute at @e[tag=emissary,tag=locator,tag=center] run particle flash ~ ~ ~ 0 0 0 0 1 force
execute at @e[tag=emissary,tag=locator,tag=center] run particle explosion ~ ~ ~ 1 1 1 0 2 force
execute as @e[tag=emissary_swirl,tag=justsummoned] at @s run function effects:emissary/__generated__/block/83