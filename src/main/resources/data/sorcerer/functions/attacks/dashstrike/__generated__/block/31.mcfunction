#built using mc-build (https://github.com/mc-build/mc-build)

execute facing entity @a[distance=..30,limit=1,gamemode=!spectator,sort=nearest] feet rotated ~ 0 run function sorcerer:attacks/dashstrike/__generated__/block/32
function animated_java:ajsorcererm2/animations/pause_all
function animated_java:ajsorcererm2/animations/idle/stop
function animated_java:ajsorcererm2/animations/dashstrike/play