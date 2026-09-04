#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s s.circle_timer 1
tp @s[tag=!shooter,tag=!Reflector,tag=!Plane,tag=!SorcererPlane,tag=!Resonance] ~ ~ ~ ~0.5 ~
execute as @s[tag=Plane,tag=!fizzling] run function spells:__generated__/block/159
execute as @s[tag=SorcererPlane] run function spells:__generated__/block/163
execute as @s[tag=Reflector] at @s positioned ^ ^1.4 ^-1.3 run function spells:__generated__/block/164
kill @s[tag=!Plane,tag=!SorcererPlane,scores={s.circle_timer=..0}]