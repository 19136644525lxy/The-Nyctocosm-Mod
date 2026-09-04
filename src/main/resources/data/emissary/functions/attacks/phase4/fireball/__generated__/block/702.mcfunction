#built using mc-build (https://github.com/mc-build/mc-build)

function animated_java:ajemissary/animations/pause_all
scoreboard players set #tween_duration aj.i 5
function animated_java:ajemissary/animations/phase4.attack.fireball/tween_play
execute facing entity @a[sort=nearest,limit=1,gamemode=!spectator,tag=!emissarydebug,distance=..300] feet run tp @s ~ ~ ~ ~ 0