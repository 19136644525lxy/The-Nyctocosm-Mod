#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=gathering] run function spells:__generated__/block/123
execute as @s[tag=!gathering] run function spells:__generated__/block/125
execute unless block ~ ~ ~ #nyctocosm:passthrough run kill @s