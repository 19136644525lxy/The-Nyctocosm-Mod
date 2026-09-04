#built using mc-build (https://github.com/mc-build/mc-build)

execute if block ~ ~2 ~ #nyctocosm:passthrough run tp @s[predicate=!nyctocosm:is_sneaking] ~ ~2 ~
execute unless block ~ ~2 ~ #nyctocosm:passthrough run tp @s[predicate=!nyctocosm:is_sneaking] ~ ~ ~
tp @s[predicate=nyctocosm:is_sneaking] ~ ~ ~
execute at @s run playsound nyctocosm:spells.impact.transmittance master @a[distance=..50] ~ ~ ~ 7
execute at @s positioned ~ ~1 ~ run function spells:transmittance/__generated__/block/479
scoreboard players set @s s.transmittance_timer 30
effect give @s slow_falling 6 4 true
scoreboard players reset TRANGE value
tag @s remove endraycast