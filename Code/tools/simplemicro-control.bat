for /l %%r in (10, 10, 100) do (
	jmeter-n.cmd Simplemicro-control.jmx -Jathletics=%%r
)