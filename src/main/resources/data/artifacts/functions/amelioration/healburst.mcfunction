#built using mc-build (https://github.com/mc-build/mc-build)

function magic:calcstatsspell
scoreboard players operation a.temp a.value = @s m.radiance.power
scoreboard players operation a.temp a.value += @s m.global.power
execute as @s[distance=..6] at @s run function artifacts:amelioration/__generated__/block/3