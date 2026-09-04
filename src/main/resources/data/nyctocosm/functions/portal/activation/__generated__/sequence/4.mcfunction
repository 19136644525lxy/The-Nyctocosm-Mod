#built using mc-build (https://github.com/mc-build/mc-build)

execute at @e[tag=portal,tag=origin,tag=activating] run function nyctocosm:portal/activation/summon_external_resonances_second
execute as @e[tag=resonator] at @s rotated as @e[tag=portal,tag=origin] run tp @s ~ ~ ~ ~ ~
scoreboard players set @e[tag=resonator,tag=second] timer -900